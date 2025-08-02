import React, { useState, useEffect } from 'react';

function StaticRecentScan() {
  const [view, setView] = useState('overall'); // Default to overall
  const [pageWiseData, setPageWiseData] = useState([]);
  const [overallData, setOverallData] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    Promise.all([
      fetch('/mock-data/static-analysis-report.json').then(res => res.json()),
      fetch('/mock-data/static-analysis-recent-overall.json').then(res => res.json())
    ]).then(([pageData, globalData]) => {
      setPageWiseData(pageData.files);
      setOverallData(globalData);
      setLoading(false);
    });
  }, []);

  if (loading) {
    return <div className="static-recent-scan"><h2>Recent Scan</h2><p>Loading...</p></div>;
  }

  return (
    <div className="static-recent-scan">
      <div className="widget-header">
        <h2>Recent Scan {overallData && overallData.timestamp && `(Scanned on: ${new Date(overallData.timestamp).toLocaleString()})`}</h2>
        <div className="view-toggle">
          <button onClick={() => setView('overall')} className={view === 'overall' ? 'active' : ''}>Overall</button>
          <button onClick={() => setView('pagewise')} className={view === 'pagewise' ? 'active' : ''}>Page-wise</button>
        </div>
      </div>

      {view === 'pagewise' && pageWiseData.length > 0 ? (
        pageWiseData.map((file, index) => (
          <div key={index} className="file-report">
            <div className="file-summary">
              <strong>File:</strong> {file.filePath} | 
              <strong>Errors:</strong> {file.errorCount} | 
              <strong>Warnings:</strong> {file.warningCount}
            </div>
            <table className="results-table">
              <thead>
                <tr>
                  <th>Rule ID</th>
                  <th>Severity</th>
                  <th>Message</th>
                  <th>Location</th>
                </tr>
              </thead>
              <tbody>
                {file.messages.map((message, msgIndex) => (
                  <tr key={msgIndex}>
                    <td>{message.ruleId}</td>
                    <td>{message.severity}</td>
                    <td>{message.message}</td>
                    <td>{`${message.line}:${message.column} (${message.nodeType})`}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        ))
      ) : view === 'pagewise' && <p>No page-wise data available.</p>}

      {view === 'overall' && overallData ? (
        <div className="file-report">
          <div className="file-summary">
            <strong>Total Errors:</strong> {overallData.errorCount} | 
            <strong>Total Warnings:</strong> {overallData.warningCount}
          </div>
          <table className="results-table">
            <thead>
              <tr>
                <th>File</th>
                <th>Rule ID</th>
                <th>Severity</th>
                <th>Message</th>
                <th>Location</th>
              </tr>
            </thead>
            <tbody>
              {overallData.messages && overallData.messages.map((message, msgIndex) => (
                <tr key={msgIndex}>
                  <td>{message.filePath}</td>
                  <td>{message.ruleId}</td>
                  <td>{message.severity}</td>
                  <td>{message.message}</td>
                  <td>{`${message.line}:${message.column} (${message.nodeType})`}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      ) : view === 'overall' && <p>No overall data available.</p>}
    </div>
  );
}

export default StaticRecentScan;



