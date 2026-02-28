// frontend/src/shared/components/SearchBar/SearchBar.js 
// 
import "./SearchBar.css";

export default function SearchBar({ title, accessionNumber, onChange }) {
  return (
    <div className="search-bar">
      <input
        placeholder="Search by title..."
        value={title}
        onChange={(e) => onChange("title", e.target.value)}
      />

      <input
        placeholder="Search by accession number..."
        value={accessionNumber}
        onChange={(e) => onChange("accessionNumber", e.target.value)}
      />
    </div>
  );
}
