import React from 'react';
import './ToggleSwitch.css';

function ToggleSwitch({ selected, onSelect }) {
  const options = ['Static', 'Dynamic', 'Manual'];

  return (
    <div className="toggle-switch">
      {options.map(option => (
        <button
          key={option}
          className={`toggle-option ${selected === option.toLowerCase() ? 'active' : ''}`}
          onClick={() => onSelect(option.toLowerCase())}
        >
          {option} Analysis
        </button>
      ))}
    </div>
  );
}

export default ToggleSwitch;
