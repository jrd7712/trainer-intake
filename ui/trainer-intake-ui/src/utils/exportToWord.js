// utils/exportToWord.js
import { Document, Packer, Paragraph, TextRun } from "docx";
import { saveAs } from "file-saver";

export async function exportToWord(program) {
  const doc = new Document({
    sections: [
      {
        children: [
          new Paragraph({
            children: [
              new TextRun({
                text: "Workout Program",
                bold: true,
                size: 32,
              }),
            ],
          }),

          new Paragraph(`Survey #: ${program.surveyNumber ?? "N/A"}`),
          new Paragraph(`Created At: ${new Date(program.createdAt).toLocaleString()}`),
          new Paragraph(""),

          new Paragraph({
            children: [
              new TextRun({
                text: "Program Plan",
                bold: true,
                size: 28,
              }),
            ],
          }),

          new Paragraph(""),

          ...program.planText.split("\n").map(
            (line) =>
              new Paragraph({
                children: [new TextRun({ text: line })],
              })
          ),
        ],
      },
    ],
  });

  const blob = await Packer.toBlob(doc);
  saveAs(blob, `program-${program.id}.docx`);
}