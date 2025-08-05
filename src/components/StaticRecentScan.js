import React, { useState, useEffect } from 'react';
import TruncatedText from './TruncatedText';
import SeverityLabel from './SeverityLabel';


function StaticRecentScan() {
  const [view, setView] = useState('overall'); // Default to overall
  const [pageWiseData, setPageWiseData] = useState([]);
  const [overallData, setOverallData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [selectedFile, setSelectedFile] = useState(null);

  const handleFileClick = (filePath) => {
    setSelectedFile(selectedFile === filePath ? null : filePath);
  };

  useEffect(() => {
    const fetchRecentScanData = async () => {
      try {
        const response = await fetch('/api/lint-reports/recent-pagewise');
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
        const sortedFiles = data.files.sort((a, b) => b.errorCount - a.errorCount);
        setPageWiseData(sortedFiles);
        // For overall data, we can derive it from pageWiseData or fetch separately if needed.
        // For now, let's calculate a simple overall summary from the page-wise data.
        const overallErrorCount = data.files.reduce((sum, file) => sum + file.errorCount, 0);
        const overallWarningCount = data.files.reduce((sum, file) => sum + file.warningCount, 0);
        setOverallData({
          timestamp: data.timestamp,
          errorCount: overallErrorCount,
          warningCount: overallWarningCount,
          messages: data.files.flatMap(file => file.messages.map(msg => ({ ...msg, filePath: file.filePath })))
        });
      } catch (error) {
        console.error("Could not fetch recent scan data:", error);
        setPageWiseData([]);
        setOverallData(null);
      } finally {
        setLoading(false);
      }
    };

    fetchRecentScanData();
  }, []);

  const formatTimestamp = (timestamp) => {
    const date = new Date(timestamp);
    const day = String(date.getDate()).padStart(2, '0');
    const month = date.toLocaleString('default', { month: 'short' });
    const year = String(date.getFullYear()).slice(-2);
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const seconds = String(date.getSeconds()).padStart(2, '0');
    return `${day} ${month} ${year} ${hours}:${minutes}:${seconds}`;
  };

  if (loading) {
    return <div className="static-recent-scan"><h2>Recent Scan</h2><p>Loading...</p></div>;
  }

  return (
    <div className="static-recent-scan">
      <div className="widget-header">
        <h2>Recent Scan {overallData && overallData.timestamp && `(${formatTimestamp(overallData.timestamp)})`}</h2>
        <div className="view-toggle">
          <button onClick={() => setView('overall')} className={view === 'overall' ? 'active' : ''}>Overall</button>
          <button onClick={() => setView('pagewise')} className={view === 'pagewise' ? 'active' : ''}>Page-wise</button>
        </div>
      </div>

      {view === 'pagewise' && pageWiseData.length > 0 ? (
        <table className="results-table">
          <thead>
            <tr>
              <th>File</th>
              <th>Errors</th>
              <th>Warnings</th>
            </tr>
          </thead>
          <tbody>
            {pageWiseData.map((file, index) => (
              <React.Fragment key={index}>
                <tr onClick={() => handleFileClick(file.filePath)} className="clickable-row">
                  <td>{file.filePath}</td>
                  <td>{file.errorCount}</td>
                  <td>{file.warningCount}</td>
                </tr>
                {selectedFile === file.filePath && (
                  <tr>
                    <td colSpan="3">
                      <table className="results-table nested-table">
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
                              <td><SeverityLabel severity={message.severity} /></td>
                              <td>{message.message}</td>
                              <td>{`${message.line}:${message.column} (${message.nodeType})`}</td>
                            </tr>
                          ))}
                        </tbody>
                      </table>
                    </td>
                  </tr>
                )}
              </React.Fragment>
            ))}
          </tbody>
        </table>
      ) : view === 'pagewise' && <p>No page-wise data available.</p>}

      {view === 'overall' && overallData ? (
        <div className="file-report">
          <div className="overall-summary">
            <div className="summary-box errors">
              <div className="summary-value">{overallData.errorCount}</div>
              <div className="summary-label">Total Errors</div>
            </div>
            <div className="summary-box warnings">
              <div className="summary-value">{overallData.warningCount}</div>
              <div className="summary-label">Total Warnings</div>
            </div>
          </div>
          <table className="results-table">
            <thead>
              <tr>
                <th className="file-col">File</th>
                <th>Rule ID</th>
                <th>Severity</th>
                <th>Message</th>
                <th>Location</th>
              </tr>
            </thead>
            <tbody>
              {overallData.messages && overallData.messages.map((message, msgIndex) => (
                <tr key={msgIndex}>
                  <td className="file-col">{message.filePath}</td>
                  <td>{message.ruleId}</td>
                  <td><SeverityLabel severity={message.severity} /></td>
                  <td><TruncatedText text={message.message} /></td>
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
