// components/DownloadButtons.jsx
import React from "react";
import { exportToPDF } from "../utils/exportToPDF";
import { exportToWord } from "../utils/exportToWord";
import "./DownloadButtons.css";

function DownloadButtons({ program }) {
  return (
    <div className="download-buttons">
      <button className="download-btn" onClick={() => exportToPDF(program)}>
        Download PDF
      </button>

      <button className="download-btn" onClick={() => exportToWord(program)}>
        Download Word
      </button>
    </div>
  );
}

export default DownloadButtons;