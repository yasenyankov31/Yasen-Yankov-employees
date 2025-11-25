import { useState } from "react";
import "./App.css";

function App() {
  const [file, setFile] = useState(null);
  const [datePattern, setDatePattern] = useState("yyyy-MM-dd");
  const [result, setResult] = useState(null);

  const datePatterns = ["yyyy-MM-dd", "MM/dd/yyyy", "dd-MM-yyyy", "dd/MM/yyyy"];

  const handleFileChange = (e) => setFile(e.target.files[0]);
  const handlePatternChange = (e) => setDatePattern(e.target.value);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!file) {
      alert("Please select a file!");
      return;
    }

    const formData = new FormData();
    formData.append("file", file);
    formData.append("datePattern", datePattern);

    try {
      const response = await fetch(
        "http://localhost:8080/api/pair-finder/upload",
        {
          method: "POST",
          body: formData,
        }
      );

      if (!response.ok) throw new Error("Upload failed");

      const files = await response.json();
      setResult([files]); // put in array for table rendering
    } catch (err) {
      console.error(err);
      alert("Error uploading file");
    }
  };

  return (
    <div className="container">
      <h1 className="title">Pair Finder Upload</h1>

      <form onSubmit={handleSubmit} className="form">
        <div className="input-group">
          <label className="label">
            Select CSV file:
            <input
              type="file"
              accept=".csv"
              onChange={handleFileChange}
              className="file-input"
            />
          </label>
        </div>

        <div className="input-group">
          <label className="label">
            Select date pattern:
            <select
              value={datePattern}
              onChange={handlePatternChange}
              className="select"
            >
              {datePatterns.map((pattern) => (
                <option key={pattern} value={pattern}>
                  {pattern}
                </option>
              ))}
            </select>
          </label>
        </div>

        <button type="submit" className="button">
          Upload
        </button>
      </form>

      {result && result.length > 0 && (
        <>
          <h2 className="result-title">Result</h2>{" "}
          <table className="result-table">
            <thead>
              <tr>
                <th>Employee 1 ID</th>
                <th>Employee 2 ID</th>
                <th>Project ID</th>
                <th>Time Worked Together (days)</th>
              </tr>
            </thead>
            <tbody>
              {result.map((row, index) => (
                <tr key={index}>
                  <td>{row.firstEmployeeId}</td>
                  <td>{row.secondEmployeeId}</td>
                  <td>{row.projectId}</td>
                  <td>{row.timeSpent}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </>
      )}
    </div>
  );
}

export default App;
