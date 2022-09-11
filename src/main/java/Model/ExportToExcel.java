/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author AdminOmarGuevara
 */
public class ExportToExcel {

    public void toXLS(JTable[] tables) throws IOException {
        
        List<String> tableNames = new ArrayList<>();
        tableNames.add("Prestamos");
        tableNames.add("Top Libros");
        tableNames.add("Prestamos por Carrera"); 
        tableNames.add("Usuarios mas Multados");
        tableNames.add("Usuarios más Activos");
        
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de excel", "xls");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Guardar archivo");
        chooser.setAcceptAllFileFilterUsed(false);
        
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            String ruta = chooser.getSelectedFile().toString().concat(".xls");
            try {
                File archivoXLS = new File(ruta);
                if (archivoXLS.exists()) {
                    archivoXLS.delete();
                }
                archivoXLS.createNewFile();

                Workbook libro = new HSSFWorkbook(); // Es el libro de Excel
                
                for(JTable table : tables){
                    FileOutputStream archivo = new FileOutputStream(archivoXLS);
                    Sheet hoja = libro.createSheet(tableNames.get(0)); // Una hoja de Excel
                    tableNames.remove(0);
                    hoja.setDisplayGridlines(false);
                    for (int f = 0; f < table.getRowCount(); f++) {
                        Row fila = hoja.createRow(f);
                        for (int c = 0; c < table.getColumnCount(); c++) {
                            Cell celda = fila.createCell(c);
                            if (f == 0) {
                                celda.setCellValue(table.getColumnName(c));
                            }
                        }
                    }

                    int filaInicio = 1;
                    for (int f = 0; f < table.getRowCount(); f++) {
                        Row fila = hoja.createRow(filaInicio);
                        filaInicio++;
                        for (int c = 0; c < table.getColumnCount(); c++) {
                            Cell celda = fila.createCell(c);
                            if (table.getValueAt(f, c) instanceof Double) {
                                celda.setCellValue(Double.parseDouble(table.getValueAt(f, c).toString()));
                            } else if (table.getValueAt(f, c) instanceof Float) {
                                celda.setCellValue(Float.parseFloat((String) table.getValueAt(f, c)));
                            } else {
                                celda.setCellValue(String.valueOf(table.getValueAt(f, c)));
                            }
                        }
                    }

                    libro.write(archivo);
                    archivo.close();
                }
                
                Desktop.getDesktop().open(archivoXLS);
            } catch (IOException | NumberFormatException e) {
                throw e;
            }
        }
    }
}
