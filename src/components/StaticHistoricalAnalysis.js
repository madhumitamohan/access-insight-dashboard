import React, { useState, useEffect } from 'react';
import { Line } from 'react-chartjs-2';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';
import './StaticHistoricalAnalysis.css';

// Register Chart.js components
ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend
);

function StaticHistoricalAnalysis() {
  const [view, setView] = useState('overall'); // 'overall' or 'pagewise'
  const [chartData, setChartData] = useState({
    labels: [],
    datasets: [],
  });
  const [pageWiseHistoricalData, setPageWiseHistoricalData] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    Promise.all([
      fetch('/mock-data/static-analysis-historical-overall.json').then(res => res.json()),
      fetch('/mock-data/static-analysis-historical-pagewise.json').then(res => res.json())
    ])
      .then(([overallHistory, pagewiseHistory]) => {
        // Process overall history for chart
        const aggregatedData = {};
        overallHistory.historicalData.forEach(run => {
          const date = new Date(run.timestamp).toLocaleDateString(undefined, { year: 'numeric', month: 'short', day: 'numeric' });
          if (!aggregatedData[date]) {
            aggregatedData[date] = { errorCount: 0, warningCount: 0 };
          }
          aggregatedData[date].errorCount += run.errorCount;
          aggregatedData[date].warningCount += run.warningCount;
        });

        const labels = Object.keys(aggregatedData).sort((a, b) => new Date(a) - new Date(b));
        const errorCounts = labels.map(label => aggregatedData[label].errorCount);
        const warningCounts = labels.map(label => aggregatedData[label].warningCount);

        setChartData({
          labels: labels,
          datasets: [
            {
              label: 'Errors',
              data: errorCounts,
              borderColor: 'rgb(255, 99, 132)', // Red
              backgroundColor: 'rgba(255, 99, 132, 0.5)',
              tension: 0.1,
            },
            {
              label: 'Warnings',
              data: warningCounts,
              borderColor: 'rgb(255, 159, 64)', // Orange
              backgroundColor: 'rgba(255, 159, 64, 0.5)',
              tension: 0.1,
            },
          ],
        });

        // Set pagewise history
        setPageWiseHistoricalData(pagewiseHistory);
        setLoading(false);
      })
      .catch(error => {
        console.error('Error fetching historical data:', error);
        setLoading(false);
      });
  }, []);

  const options = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
      },
      title: {
        display: true,
        text: 'Historical Error and Warning Trends',
      },
    },
    scales: {
      y: {
        ticks: {
          precision: 0,
        },
      },
    },
  };

  if (loading) {
    return (
      <div className="static-historical-analysis">
        <h2>Historical Analysis</h2>
        <p>Loading historical data...</p>
      </div>
    );
  }

  return (
    <div className="static-historical-analysis">
      <div className="widget-header">
        <h2>Historical Analysis</h2>
        <div className="view-toggle">
          <button onClick={() => setView('overall')} className={view === 'overall' ? 'active' : ''}>Overall</button>
          <button onClick={() => setView('pagewise')} className={view === 'pagewise' ? 'active' : ''}>Page-wise</button>
        </div>
      </div>

      {view === 'overall' && chartData.labels.length > 0 ? (
        <Line options={options} data={chartData} />
      ) : view === 'overall' && <p>No overall historical data available to display chart.</p>}

      {view === 'pagewise' && pageWiseHistoricalData.length > 0 ? (
        <div className="pagewise-historical-container">
          {pageWiseHistoricalData.map((fileEntry, fileIndex) => (
            <div key={fileIndex} className="file-historical-report">
              <h3>{fileEntry.filePath}</h3>
              <table className="historical-table">
                <thead>
                  <tr>
                    <th>Date</th>
                    <th>Errors</th>
                    <th>Warnings</th>
                  </tr>
                </thead>
                <tbody>
                  {fileEntry.historicalData.map((run, runIndex) => (
                    <tr key={runIndex}>
                      <td>{new Date(run.timestamp).toLocaleDateString(undefined, { year: 'numeric', month: 'short', day: 'numeric' })}</td>
                      <td>{run.errorCount}</td>
                      <td>{run.warningCount}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          ))}
        </div>
      ) : view === 'pagewise' && <p>No page-wise historical data available.</p>}
    </div>
  );
}

export default StaticHistoricalAnalysis;
