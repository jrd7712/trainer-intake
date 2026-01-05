import "./ProgressBar.css";

function ProgressBar({ progress }) {
  return (
    <div className="progress-bar-container">
      <div
        className="progress-bar-fill"
        style={{ width: `${progress * 100}%` }}
      />
    </div>
  );
}

export default ProgressBar;