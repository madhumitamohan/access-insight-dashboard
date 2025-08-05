import React from 'react';
import './SeverityLabel.css';

function SeverityLabel({ severity }) {
  const getSeverityProps = () => {
    switch (severity) {
      case 2:
        return { label: 'Error', className: 'severity-error' };
      case 1:
        return { label: 'Warning', className: 'severity-warning' };
      default:
        return { label: `Unknown (${severity})`, className: 'severity-unknown' };
    }
  };

  const { label, className } = getSeverityProps();

  return <span className={`severity-label ${className}`}>{label}</span>;
}

export default SeverityLabel;
