import React, { useState } from 'react';
import './TruncatedText.css';

function TruncatedText({ text, maxLength = 50 }) {
  const [isTruncated, setIsTruncated] = useState(true);

  const toggleTruncate = () => {
    setIsTruncated(!isTruncated);
  };

  if (text.length <= maxLength) {
    return <span>{text}</span>;
  }

  return (
    <span>
      {isTruncated ? `${text.substring(0, maxLength)}...` : text}
      <button onClick={toggleTruncate} className="read-more-btn">
        {isTruncated ? 'Read More' : 'Read Less'}
      </button>
    </span>
  );
}

export default TruncatedText;
