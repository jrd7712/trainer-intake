// utils/exportToPDF.js
import jsPDF from "jspdf";

export function exportToPDF(program) {
  const doc = new jsPDF({
    unit: "pt",
    format: "letter",
  });

  const margin = 40;
  const pageHeight = doc.internal.pageSize.height;
  let y = margin;

  // Title
  doc.setFont("Helvetica", "bold");
  doc.setFontSize(20);
  doc.text("Workout Program", margin, y);
  y += 30;

  // Metadata
  doc.setFontSize(12);
  doc.setFont("Helvetica", "normal");
  doc.text(`Survey #: ${program.surveyNumber ?? "N/A"}`, margin, y);
  y += 18;
  doc.text(`Created At: ${new Date(program.createdAt).toLocaleString()}`, margin, y);
  y += 30;

  // Section Header
  doc.setFont("Helvetica", "bold");
  doc.setFontSize(14);
  doc.text("Program Plan", margin, y);
  y += 20;

  // Body text
  doc.setFont("Helvetica", "normal");
  doc.setFontSize(12);

  const lines = doc.splitTextToSize(program.planText, 520);

  lines.forEach((line) => {
    // If we're near the bottom, add a new page
    if (y > pageHeight - margin) {
      doc.addPage();
      y = margin;
    }

    doc.text(line, margin, y);
    y += 16; // line spacing
  });

  doc.save(`program-${program.id}.pdf`);
}