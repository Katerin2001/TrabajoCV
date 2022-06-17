/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabajo1cv;

import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class Estudiantes extends javax.swing.JFrame {
       Integer fila;
    /**
     * Creates new form Estudiantes
     */
    public Estudiantes() {
        initComponents();
        bloquearTextos();
        bloquearBotones();
        cargarTabla();
       
        jtblEstudiantes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if(jtblEstudiantes.getSelectedRow()!=-1){
                     fila=jtblEstudiantes.getSelectedRow();
                    jtxtCedula.setText(jtblEstudiantes.getValueAt(fila,0).toString());
                    jtxtNombre.setText(jtblEstudiantes.getValueAt(fila, 1).toString());
                    jtxtApellido.setText(jtblEstudiantes.getValueAt(fila, 2).toString());
                    jtxtDireccion.setText(jtblEstudiantes.getValueAt(fila, 3).toString());
                    jtxtTelefono.setText(jtblEstudiantes.getValueAt(fila, 4).toString());
                    desbloquearTextos();
                    desbloquearbotonesEditar();
                }

            }
        });
    }
    public void desbloquearbotonesEditar(){
        jbtnNuevo.setEnabled(false);
        jbtnGuardar.setEnabled(false);
        jBtnActualizar.setEnabled(true);
        jbtnBorrar.setEnabled(true);
        jbtnCancelar.setEnabled(true);
        jbtnCancelar.setEnabled(true);
    }
    public void bloquearTextos(){
        jtxtCedula.setEnabled(false);
        jtxtNombre.setEnabled(false);
        jtxtApellido.setEnabled(false);
        jtxtTelefono.setEnabled(false);
        jtxtDireccion.setEnabled(false);
    }
    public void desbloquearTextos(){
        jtxtCedula.setEnabled(true);
        jtxtNombre.setEnabled(true);
        jtxtApellido.setEnabled(true);
        jtxtTelefono.setEnabled(true);
        jtxtDireccion.setEnabled(true);
    }
    public void limpiarTextos(){
        jtxtCedula.setText("");
        jtxtNombre.setText(" ");
        jtxtApellido.setText(" ");
        jtxtTelefono.setText(" ");
        jtxtDireccion.setText(" ");
    }
        
    public void  bloquearBotones(){
        jbtnNuevo.setEnabled(true );
        jbtnGuardar.setEnabled(false);
        jBtnActualizar.setEnabled(false);
        jbtnBorrar.setEnabled(false);
        jbtnCancelar.setEnabled(false);
        jbtnGuardar.setEnabled(false);   
    }
    public void desbloquearBotones(){
        jbtnNuevo.setEnabled(false);
        jbtnGuardar.setEnabled(true);
        jBtnActualizar.setEnabled(false);
        jbtnBorrar.setEnabled(false);
        jbtnCancelar.setEnabled(true);
        //jbtnGuardar.setEnabled(false);  
        
    }
            
            
    public void guardar(){
        if(jtxtCedula.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Ingrese una cedula ");
            jtxtCedula.requestFocus();
        }else if(jtxtNombre.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Ingrese un Nombre ");
            jtxtCedula.requestFocus();
            
        }
        else if(jtxtApellido.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Ingrese una Apellido ");
            jtxtCedula.requestFocus();
            
        }
        
        try {
            String est_cedula,est_nombre,est_apellido,est_telefono, est_direccion;
            conexion cc = new conexion();
            Connection cn = cc.conectar();
            est_cedula=jtxtCedula.getText();
            est_nombre=jtxtNombre.getText();
            est_apellido=jtxtApellido.getText();
            est_telefono=jtxtTelefono.getText();
            est_direccion=jtxtDireccion.getText();
            if(jtxtTelefono.getText().isEmpty()){
            String smsTelefono="000000000";
            est_telefono =smsTelefono;
            //JOptionPane.showMessageDialog(this,"Ingrese una cedula ");
            //jtxtCedula.requestFocus();
            
            
        }else if(jtxtDireccion.getText().isEmpty()){
            String direcc="S/N";
            est_direccion= direcc;
        }
            String sql= "insert into cuartosw (est_cedula,est_nombre,est_apellido,est_telefono,est_direccion,est_estado) values (?,?,?,?,?,1)";
            java.sql.PreparedStatement psd = cn.prepareStatement(sql);
            psd.setString(1, est_cedula);
            psd.setString(2, est_nombre);
            psd.setString(3, est_apellido);
            psd.setString(4, est_telefono);
            psd.setString(5, est_direccion);
            //psd.setString(6, "est_estado");
           int n= psd.executeUpdate();
           if(n>0){
               JOptionPane.showMessageDialog(this, "Se inserto Correctamente");
               cargarTabla();
               
               
           }
        } catch (SQLException ex) {
         JOptionPane.showMessageDialog(this,"No se realizo la transaccion, error !!");
        }
    }
    public void cargarTabla(){
        try {
            DefaultTableModel modelo= new DefaultTableModel();
            
            String titulos[]={"Cedula","Nombre","Apellido","Telefono","Direccion"};
            String [] registros = new String [5];
            modelo= new DefaultTableModel(null,titulos);
            int estado;
            jtblEstudiantes.setModel(modelo);
            conexion cc = new conexion();
            Connection cn = cc.conectar();
            String sql = "";
            sql = "select * from cuartosw";
            Statement psd=cn.createStatement();
            ResultSet rs= psd.executeQuery(sql);
            while(rs.next()){
                registros[0]=rs.getString("est_cedula");
                registros[1]=rs.getString("est_nombre");
                registros[2]=rs.getString("est_apellido");
                registros[3]=rs.getString("est_telefono");
                registros[4]=rs.getString("est_direccion");
                estado=Integer.valueOf(rs.getString("est_estado"));
                if (estado==1) {
                    modelo.addRow(registros);
                }
            }
            jtblEstudiantes.setModel(modelo);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }
    public void actualizar(){
        String valorTelefon="S/N";
        if(jtxtCedula.getText().isEmpty()|| jtxtCedula.getText()==""){
            
            JOptionPane.showMessageDialog(null,"Ingrese una cedula");
            jtxtCedula.requestFocus();
        }else if(jtxtNombre.getText().isEmpty()|| jtxtNombre.getText()==""){
            JOptionPane.showMessageDialog(this,"Ingrese un Nombre");
            jtxtNombre.requestFocus();
        }else if(jtxtApellido.getText().isEmpty()|| jtxtApellido.getText()==""){
            JOptionPane.showMessageDialog(this,"Ingrese un Apellido");
            jtxtApellido.requestFocus();
//        }else if(jtxtDireccion.getText().isEmpty()|| jtxtDireccion.getText()==""){
//            JOptionPane.showMessageDialog(this,"Ingrese una Direccion");
//            jtxtDireccion.requestFocus();
        //}else if(jtxtTelefono.getText().isEmpty()|| jtxtTelefono.getText()==""){
//            String valorTelefon="S/N";
        }else{
        try {
            // TODO add your handling code here:
            conexion cc= new conexion ();
            Connection cn=cc.conectar();
            String sql="";
            sql="update cuartosw set est_nombre='"+jtxtNombre.getText()+"',est_apellido='"+jtxtApellido.getText()+"',est_direccion='"+jtxtDireccion.getText()
                    +"',est_telefono='"+jtxtTelefono.getText()+"'Where est_cedula='"+jtxtCedula.getText()+"'";
            java.sql.PreparedStatement psd=  cn.prepareStatement(sql);
            int n=psd.executeUpdate();
            if(n>0){
                JOptionPane.showMessageDialog(this,"Actualizada correctamente");
                cargarTabla();
                limpiarTextos();
                bloquearBotones();
                bloquearTextos();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Estudiantes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    }
   
      

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jtxtCedula = new javax.swing.JTextField();
        jtxtNombre = new javax.swing.JTextField();
        jtxtApellido = new javax.swing.JTextField();
        jtxtTelefono = new javax.swing.JTextField();
        jtxtDireccion = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jbtnNuevo = new javax.swing.JButton();
        jbtnGuardar = new javax.swing.JButton();
        jBtnActualizar = new javax.swing.JButton();
        jbtnBorrar = new javax.swing.JButton();
        jbtnCancelar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtblEstudiantes = new javax.swing.JTable();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("Cedula:");

        jLabel2.setText("Nombre:");

        jLabel3.setText("Apellido:");

        jLabel4.setText("Direccion");

        jLabel5.setText("Telefono");

        jtxtTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtTelefonoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jtxtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtxtApellido, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jtxtNombre)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jtxtDireccion))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtxtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jtxtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jtxtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jtxtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jbtnNuevo.setText("Nuevo");
        jbtnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNuevoActionPerformed(evt);
            }
        });

        jbtnGuardar.setText("Guardar");
        jbtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnGuardarActionPerformed(evt);
            }
        });

        jBtnActualizar.setText("Actualizar");
        jBtnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnActualizarActionPerformed(evt);
            }
        });

        jbtnBorrar.setText("Borrar");
        jbtnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnBorrarActionPerformed(evt);
            }
        });

        jbtnCancelar.setText("Cancelar");
        jbtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jbtnBorrar))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jBtnActualizar)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jbtnGuardar)
                            .addComponent(jbtnNuevo))
                        .addComponent(jbtnCancelar)))
                .addContainerGap(70, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbtnNuevo)
                .addGap(12, 12, 12)
                .addComponent(jbtnGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnActualizar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbtnBorrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbtnCancelar)
                .addContainerGap())
        );

        jtblEstudiantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(jtblEstudiantes);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtxtTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtTelefonoActionPerformed

    private void jbtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGuardarActionPerformed
        guardar();
        bloquearBotones();
        bloquearTextos();
        limpiarTextos();
    }//GEN-LAST:event_jbtnGuardarActionPerformed

    private void jbtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNuevoActionPerformed
        // TODO add your handling code here:
        desbloquearTextos();
        desbloquearBotones();
    }//GEN-LAST:event_jbtnNuevoActionPerformed

    private void jBtnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnActualizarActionPerformed
        // TODO add your handling code here:
        actualizar();
        System.out.println("");
        
    }//GEN-LAST:event_jBtnActualizarActionPerformed

    private void jbtnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnBorrarActionPerformed
        // TODO add your handling code here:
        borrarEstudiante();
    }//GEN-LAST:event_jbtnBorrarActionPerformed

    private void jbtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCancelarActionPerformed
        // TODO add your handling code here:
        limpiarTextos();
        bloquearTextos();
        bloquearBotones();
    }//GEN-LAST:event_jbtnCancelarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Estudiantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Estudiantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Estudiantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Estudiantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Estudiantes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnActualizar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JButton jbtnBorrar;
    private javax.swing.JButton jbtnCancelar;
    private javax.swing.JButton jbtnGuardar;
    private javax.swing.JButton jbtnNuevo;
    private javax.swing.JTable jtblEstudiantes;
    private javax.swing.JTextField jtxtApellido;
    private javax.swing.JTextField jtxtCedula;
    private javax.swing.JTextField jtxtDireccion;
    private javax.swing.JTextField jtxtNombre;
    private javax.swing.JTextField jtxtTelefono;
    // End of variables declaration//GEN-END:variables

    private void borrarEstudiante() {
        try {
               String est_cedula;
               conexion cc = new conexion();
               Connection cn = cc.conectar();
               est_cedula = jtxtCedula.getText();
               if (JOptionPane.showConfirmDialog(rootPane, "Desea Eliminar ")==0) {
                   String sql = "update  cuartosw set est_estado=0 where est_cedula= '"+ est_cedula+"'";
               java.sql.PreparedStatement psd = cn.prepareStatement(sql);
               int n = psd.executeUpdate();
               if(n>0){
                JOptionPane.showMessageDialog(this,"Eliminado satisfactoriamente");
                cargarTabla();
                limpiarTextos();
                bloquearBotones();
                bloquearTextos();
               }else{
                   System.out.println("No se ha eliminado");
               }
               
            }
           } catch (SQLException ex) {
               Logger.getLogger(Estudiantes.class.getName()).log(Level.SEVERE, null, ex);
           }
    }
}
