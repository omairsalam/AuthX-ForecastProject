/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import org.jdesktop.swingx.prompt.PromptSupport;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.testng.internal.Graph;

/**
 *
 * @author alam
 */
public class MainFrame extends javax.swing.JFrame {
    
    private static ArrayList<String> tagArray = new ArrayList<>();
    private static ArrayList<JFreeChart> chartList = new ArrayList<>();
    private static HashMap<String, Role> myRoleMap = new HashMap<>(); 
    private static Date startDate;
    private static Date endDate; 

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        startDatePicker.setDate(new Date()); //set the start date picker to today's date
        endDatePicker.setDate(getEndDate(startDatePicker.getDate()));
       
        //Get current number of employees
       // String currentRole = rolePicker.getSelectedItem().toString();
        myRoleMap = Driver.execute.getRoleMap();
        //Driver.execute.populateForcastDyanamic(start_date, end_date);
      //  int numEmployees = myRoleMap.get(currentRole).getNumEmployees();
     //   numEmpText.setText(String.valueOf(numEmployees));
     recalculateData.setEnabled(false); //recalculate should be false initially 
     PromptSupport.setPrompt("Number of Employees", employeeNumString); //Sets placeholder text for password 
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        authXLogo = new javax.swing.JLabel();
        rolePicker = new javax.swing.JComboBox<>();
        graphPanel = new javax.swing.JPanel();
        rolePickerLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        employeeNumString = new javax.swing.JTextField();
        datePicker = new javax.swing.JLabel();
        startDatePicker = new org.jdesktop.swingx.JXDatePicker();
        datePicker1 = new javax.swing.JLabel();
        endDatePicker = new org.jdesktop.swingx.JXDatePicker();
        recalculateData = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        authXLogo.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        authXLogo.setText("AuthX");

        rolePicker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rolePickerActionPerformed(evt);
            }
        });

        graphPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout graphPanelLayout = new javax.swing.GroupLayout(graphPanel);
        graphPanel.setLayout(graphPanelLayout);
        graphPanelLayout.setHorizontalGroup(
            graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 360, Short.MAX_VALUE)
        );
        graphPanelLayout.setVerticalGroup(
            graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 401, Short.MAX_VALUE)
        );

        rolePickerLabel.setText("Role Picker");

        jLabel1.setText("Number Of Employees");

        employeeNumString.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeNumStringActionPerformed(evt);
            }
        });

        datePicker.setText("Start Date");

        startDatePicker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startDatePickerActionPerformed(evt);
            }
        });

        datePicker1.setText("End Date");

        endDatePicker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endDatePickerActionPerformed(evt);
            }
        });

        recalculateData.setText("Recalculate");
        recalculateData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recalculateDataActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(rolePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(rolePickerLabel))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(employeeNumString, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(graphPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(datePicker)
                    .addComponent(startDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(datePicker1)
                    .addComponent(endDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(recalculateData))
                .addContainerGap(53, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(authXLogo)
                .addGap(371, 371, 371))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(authXLogo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(graphPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(datePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(startDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(datePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(endDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(recalculateData))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(rolePickerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rolePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(employeeNumString, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(50, 50, 50))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rolePickerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rolePickerActionPerformed
        // TODO add your handling code here:
        showGraphs();
    }//GEN-LAST:event_rolePickerActionPerformed

    private void employeeNumStringActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeeNumStringActionPerformed
        String currentRoleName = rolePicker.getSelectedItem().toString();

        double employeeNumber = Double.parseDouble(employeeNumString.getText());


        //Role is not null
        Role role = myRoleMap.get(currentRoleName);

        role.setNumEmployees(employeeNumber);

        chartList = GraphPlotter.getAllFrames(myRoleMap);

        showGraphs();


    }//GEN-LAST:event_employeeNumStringActionPerformed

    private void startDatePickerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startDatePickerActionPerformed
        Date startDate = startDatePicker.getDate();
        Date endDate = getEndDate(startDate); //gets end dat which is 3 months ahead of start date
        endDatePicker.setDate(endDate); //sets the end date picker to this 
        recalculateData.setEnabled(true); //if we pick a start date, enable this button 
    }//GEN-LAST:event_startDatePickerActionPerformed

    private void endDatePickerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_endDatePickerActionPerformed

    }//GEN-LAST:event_endDatePickerActionPerformed

    private void recalculateDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recalculateDataActionPerformed
        recalculateData.setEnabled(false);  
        System.out.println("Start date is " + getStartOfWeek(startDatePicker.getDate()));
        System.out.println("End date is " + getStartOfWeek(endDatePicker.getDate()));
         try {
            ArrayList<JFreeChart> importedCharts = Driver.recalculateMaps(getStartOfWeek(startDatePicker.getDate()), getStartOfWeek(endDatePicker.getDate())); //get new charts based on
            //new start and end dates which have been rounded off to the first day of the week 
            myRoleMap = Driver.execute.getRoleMap(); //get the new rolemap for this chart
            getTagList(importedCharts); //get a new tag list for these charts 
            populateComboBox(); //repopulate the combo box with the new tags
            showGraphs(); //repopulate all the graphs 
        } catch (InterruptedException | ParseException | JSONException | IOException | java.text.ParseException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_recalculateDataActionPerformed

    public static void getTagList(ArrayList<JFreeChart> importedChartList){
        tagArray.clear();
        chartList = importedChartList;
        for (JFreeChart s : importedChartList){
            tagArray.add(s.getTitle().getText());
        }
    } 
    
    public void populateComboBox(){
        rolePicker.removeAll();
        rolePicker.setModel(new DefaultComboBoxModel());rolePicker.removeAll();
        for (String s : tagArray){
            rolePicker.addItem(s);
        }
    }
    
    public void showGraphs(){
        String currentRole = rolePicker.getSelectedItem().toString();
        //System.out.println("Current role picked is " + currentRole);
        graphPanel.removeAll();
        for (JFreeChart s1 : chartList){

            if (s1.getTitle().getText().equals(currentRole)){
                //System.out.println("Chart Title is " + s1.getTitle().getText() + " and Role Picked is " + currentRole);
                ChartPanel p = new ChartPanel(s1);
                p.setSize(graphPanel.getWidth(), graphPanel.getHeight());
                p.setVisible(true);
                graphPanel.add(p);
                graphPanel.repaint();

            }
        }

        double numEmployees = myRoleMap.get(currentRole).getNumEmployees();
        employeeNumString.setText(String.valueOf(numEmployees));
    }
    
    /**
     * Given a start date, increments it by a months and returns it 
     * @param startDate The date we start with 
     * @return The date incremented by one month 
     */
    public Date getEndDate(Date startDate){
        Calendar cal = Calendar.getInstance(); //make a date for 1 month after the start date
        cal.setTime(startDate);
        cal.add(Calendar.MONTH, 1);
        return cal.getTime();
    }
    
    /**
     * Given a date, gets the first day of that week
     * @param startDate The start date
     * @return The first day of that week 
     */
    public Date getStartOfWeek(Date startDate){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY); //Default first day is Sunday, lets change that to Monday 
        cal.setTime(startDate);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        System.out.println("Start of this week:       " + cal.getTime());
        return cal.getTime();
    }
    
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
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel authXLogo;
    private javax.swing.JLabel datePicker;
    private javax.swing.JLabel datePicker1;
    private javax.swing.JTextField employeeNumString;
    private org.jdesktop.swingx.JXDatePicker endDatePicker;
    private javax.swing.JPanel graphPanel;
    private javax.swing.JLabel jLabel1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    private javax.swing.JButton recalculateData;
    private javax.swing.JComboBox<String> rolePicker;
    private javax.swing.JLabel rolePickerLabel;
    private org.jdesktop.swingx.JXDatePicker startDatePicker;
    // End of variables declaration//GEN-END:variables
}
