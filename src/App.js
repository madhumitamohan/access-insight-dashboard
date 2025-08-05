import React, { useState } from 'react';
import './App.css';
import StaticRecentScan from './components/StaticRecentScan';
import './components/StaticRecentScan.css';
import StaticHistoricalAnalysis from './components/StaticHistoricalAnalysis';
import './components/StaticHistoricalAnalysis.css';
import ToggleSwitch from './components/ToggleSwitch';
import './components/ToggleSwitch.css';
import JiraIntegration from './components/JiraIntegration';
import './components/JiraIntegration.css';

function App() {
  const [website, setWebsite] = useState('');
  const [analysisType, setAnalysisType] = useState('static');

  const handleSubmit = (event) => {
    event.preventDefault();
    // This is where you would trigger the data fetch for the given website
    console.log(`Fetching ${analysisType} data for:`, website);
  };

  return (
    <div className="App">
      <h1>Access Insights Dashboard</h1>
      <form onSubmit={handleSubmit} className="website-form">
        <label htmlFor="website-input">Website URL/ID:</label>
        <input
          id="website-input"
          type="text"
          value={website}
          onChange={(e) => setWebsite(e.target.value)}
          placeholder="Enter website URL or ID"
        />
        <button type="submit">Get Insights</button>
      </form>

      <ToggleSwitch selected={analysisType} onSelect={setAnalysisType} />

      {analysisType === 'static' && (
        <>
          <StaticRecentScan />
          <StaticHistoricalAnalysis />
        </>
      )}
      {/* Add other analysis components here later */}
      <JiraIntegration />
    </div>
  );
}

export default App;
