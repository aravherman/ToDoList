package todolist;

import java.awt.Color;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

public class ToDoList extends JFrame{
    private JTextField textfield;
    private final JButton addButton, completedButton, removeButton, removeAll, sort;
    private JList<String> tasklist;
    private ArrayList<Boolean> TaskStatus;
    private DefaultListModel<String> listModel;
    
    ToDoList()
    {
        textfield = new JTextField(10);
        addButton = new JButton("Add Task");
        sort = new JButton("Sort Tasks");
        completedButton = new JButton("Task Completed");
        removeButton = new JButton("Remove Task");
        removeAll = new JButton("Remove All");
        listModel = new DefaultListModel<>();//
        tasklist = new JList<>(listModel);//
        TaskStatus = new ArrayList<>();
        
        JScrollPane scrollpane = new JScrollPane(tasklist);
        JPanel panel = new JPanel();
        
        String priority[] = {"High","Medium","Least"}; 
        JComboBox<String> getPriority = new JComboBox<>(priority);
        
        
        panel.add(getPriority);
        panel.add(new JLabel("Tasks:"));
        panel.add(textfield);
        panel.add(addButton);
        panel.add(sort);
        panel.add(removeButton);
        panel.add(completedButton);
        panel.add(scrollpane);
        panel.add(removeAll);

        panel.setBounds(500, 500, 300, 450);
        panel.setBackground(Color.blue);
        add(panel);
        
        
        addButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String input = textfield.getText();
                if(input.isBlank())
                JOptionPane.showMessageDialog(null, "Task cannot be Empty!", "Error", JOptionPane.ERROR_MESSAGE);
                    
                else if(!input.isEmpty())
                {
                    
                    input = input.trim();
                    String p = (String)getPriority.getSelectedItem();
                    listModel.addElement(input + "  PENDING" + "  [" + p.toUpperCase() + "]");
                    TaskStatus.add(false);
                    textfield.setText("");
                }       
            }
        });
        
        class task{
            String t_name, stat, pri;
            task(String t_name, String stat, String pri)
            {
                this.t_name = t_name;
                this.stat = stat;
                this.pri = pri;
            }
            
            @Override
            public String toString(){
                return t_name + "  " + stat + "  [" + pri+"]";
            }
        }
        ArrayList<task> work = new ArrayList<>();
        
        sort.addActionListener(new ActionListener()
        {
            @Override
                public void actionPerformed(ActionEvent e)
                {    for(int i = 0 ; i < listModel.size(); i++)
                        {
                        String input = listModel.get(i);
                        String[] parts = input.split("\\s{2,}");
                        if(parts.length>=2)
                            {
                                String t_name = parts[0];
                                String stat = parts[1];
                                String pri = parts[2].replace("[", "").replace("]", "");
                                work.add(new task(t_name,stat,pri));
                            }
                        }
                        List<String> order = Arrays.asList("HIGH", "MEDIUM", "LEAST");
                        work.sort((t1, t2)->
                            {
                                return Integer.compare(order.indexOf(t1.pri), order.indexOf(t2.pri));
                            });
                        //panel.removeAll();
                        listModel.clear();
                        for(task t : work)
                        {listModel.addElement(t.toString());}
                        work.clear();
                }
        });
        
        completedButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int index = tasklist.getSelectedIndex();
                if(index!=-1)
                {
                    if(!TaskStatus.get(index))
                    {
                        String task = listModel.get(index);
                        listModel.set(index, task.replace("PENDING", "COMPLETED"));
                        TaskStatus.set(index, true);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Task already Completed");
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Please select the correct task to mark completed!");
                }
            }
        });
        
        removeButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int index = tasklist.getSelectedIndex();
                if(index!=-1)
                {
                    String task = listModel.get(index);
                    if(task.contains("PENDING"))
                    JOptionPane.showMessageDialog(null, "Make sure the task is completed!!", "Pending", JOptionPane.INFORMATION_MESSAGE);
                    else
                    {
                        listModel.remove(index);
                        TaskStatus.remove(index);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Please select the correct task to remove", "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        
        removeAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String response = JOptionPane.showInputDialog(null, "Are you sure want to delete all the tasks?", "Delete All", JOptionPane.QUESTION_MESSAGE);
                if(!response.equalsIgnoreCase("no"))
                {
                    listModel.clear();
                    TaskStatus.clear();
                }
            }
        });
        
        setTitle("Task Manager");
        setSize(350,450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
    }
    public static void main(String[] args) {
        // Create JFrame instance
        SwingUtilities.invokeLater(() -> {
            new ToDoList().setVisible(true);
        });
        
    }
}
