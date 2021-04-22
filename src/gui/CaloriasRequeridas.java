package gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

public class CaloriasRequeridas extends JFrame {

    //Elementos para lee los datos
    //********
    private JTextField nombre;
    private JSpinner fechaNac;
    private JSpinner estatura;
    private JRadioButton masculino;
    private JRadioButton femenino;
    private JTextField peso;
    private JComboBox actividad;
    //*********
    private JLabel tAnios; // Para mostrar la edad
    private JLabel descripcionActividad; //Describe la actividad
    private final String ACTIVIDADES[] = {"Sedentario", "Ligeramente Activas", "Moderadamente Activas", "Muy Activas"};
    private int edad = 20; // guarda el valor calculado de la edad
    private JTextArea relacion; //Contendrá a los elementos que se agregen
    private JButton calcularCal; //Activa el calculo de kilocalorias
    private JButton borrar; //Activa borrar datos
    private JButton agregar; //Activa agregar los valores calculados a la relacion
    private JButton terminar; //Activa terminar
    private double kilocalortias;//Guarda el resultado de las kilocalorias
    private JLabel calculoKilocal; //mostrar el resultado del calculo
    private JLabel tituloRelacion; //Encabezado de la relación
    private final int MIN_EDAD = 20; //Edad minima
    private final int MAX_EDAD = 65; //Edad maxima
    private final double MIN_ESTATURA = 1.40; //Estatura minimia
    private final double MAX_ESTATURA = 1.95; //Estatura maxima
    private final String DA_NOMBRE = "Da tu nombre"; //titulo predeterminado en el campo nombre
    private final String DA_PESO = "tu peso?"; //titulo predeterminado en el campo peso
    private boolean validaNombre = false; //Indicacion de la validadcion de nombre
    private boolean validaPeso = false; //Indicación de validacion de peso

    public CaloriasRequeridas() {
        Container base = getContentPane();
        base.setLayout(new BorderLayout());
        JPanel datosN = new JPanel();
        JPanel datosC = new JPanel();
        JPanel selActividad = new JPanel();
        //AdmoAccion admoAccion = new AdmoAccion();
        JLabel titulo = new JLabel("Calculo de kilocalorias requeridas por un dia para una persona adulta");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 18));
        datosN.add(titulo);
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        datosC.add(formarPanelCampos(), BorderLayout.WEST);
        datosC.add(crearSelecionActividad(), BorderLayout.EAST);
        base.add(titulo, BorderLayout.NORTH);
        base.add(datosC, BorderLayout.CENTER);
        //el método formarPanelResultados() integra la segunda seccion de la GUI
        //base.add(formarPanelResultados(),BorderLayout.SOUTH);
        this.setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(700, 450);

    }
    public JPanel formarPanelCampos(){
        AdmonAccion admonAccion = new AdmonAccion();
        AdmonFocus admonFocus = new AdmonFocus();
        AdmonEvenTeclado admonTeclado = new AdmonEvenTeclado();
        AdmonMouseAdap admonMouse = new AdmonMouseAdap();
        //Formacion del panel para los campos de los datos
        //Se crea el panel, lo titulos, y demas elementos que los conforman
        JPanel camposPanel = new JPanel();
        //Los titulos de los campos inician su nombre con t
        JLabel tNombre = new JLabel("Nombre: ");
        JLabel tFechaNac = new JLabel("Fecha Nacimiento ");
        tFechaNac.setHorizontalAlignment(SwingConstants.RIGHT);
        tAnios = new JLabel("Años: "+MIN_EDAD);
        JLabel tPeso = new JLabel ("Peso (Kgs.): ");
        JLabel tSexo = new JLabel("Sexo: ");
        JLabel tEstatura = new JLabel("Estatura (Mts): ");
        //3b
        nombre = new JTextField(DA_NOMBRE,20);
        peso = new JTextField(DA_PESO,5);
        masculino = new JRadioButton("Masculino");
        femenino = new JRadioButton("Femenino");
        ButtonGroup gSexo = new ButtonGroup();
        gSexo.add(masculino);
        gSexo.add(femenino);
        masculino.setSelected(true);
        //3c
        Calendar fechaa = Calendar.getInstance(); //Fecha actual
        Calendar fechai = Calendar.getInstance(); //Fecha inicial
        fechai.add(Calendar.YEAR,-MIN_EDAD); //Fijar la fecha minima o más reciente
        Calendar fechaf = Calendar.getInstance(); //fecha final o mas antigua
        fechaf.add(Calendar.YEAR,-MAX_EDAD);
        //3d
        SpinnerDateModel dma = new SpinnerDateModel(new Date(),null,null,Calendar.DATE);
        fechaNac = new JSpinner(dma);
        //3f
        JSpinner.DateEditor fmda = new JSpinner.DateEditor(fechaNac,"yyyy/mm/dd");
        fechaNac.setEditor(fmda);
        //5d
        SpinnerNumberModel modEst = new SpinnerNumCiclico(MIN_ESTATURA,MAX_ESTATURA);
         estatura = new JSpinner(modEst);
         //6a
        GridBagLayout gridbag = new GridBagLayout();
        camposPanel.setLayout(gridbag);
        GridBagConstraints r = new GridBagConstraints();
        r.anchor = GridBagConstraints.WEST;
        r.fill = GridBagConstraints.HORIZONTAL;
        r.gridwidth = 1;
        r.insets = new Insets(0,5,10,0);
        camposPanel.add(tNombre,r);
        r.gridwidth = GridBagConstraints.REMAINDER;
        r.ipadx = 0;
        camposPanel.add(nombre, r);
        r.gridwidth = 1;
        r.fill = GridBagConstraints.NONE;
        camposPanel.add(tFechaNac,r);
        camposPanel.add(fechaNac,r);
        r.gridwidth = GridBagConstraints.REMAINDER;
        r.fill = GridBagConstraints.NONE;
        camposPanel.add(tAnios,r);
        //Estatura y peso
        r.gridwidth = 1;
        camposPanel.add(tEstatura,r);
        r.gridwidth = 1;
        r.gridwidth = GridBagConstraints.REMAINDER;
        r.ipadx = GridBagConstraints.HORIZONTAL;
        camposPanel.add(estatura,r);
        r.gridwidth = 1;
        r.fill = GridBagConstraints.NONE;
        camposPanel.add(tPeso,r);
        r.gridwidth = GridBagConstraints.REMAINDER;
        r.fill = GridBagConstraints.NONE;
        camposPanel.add(peso,r);
        //Sexo
        JPanel ps = new JPanel();
        ps.add(tSexo);
        ps.add(masculino);
        ps.add(femenino);
        r.insets = new Insets(0,0,15,0);
        r.gridwidth = GridBagConstraints.NONE;
        camposPanel.add(ps,r);
        camposPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Propociona los datos"),
                        BorderFactory.createEmptyBorder(5,5,10,5)
                )
        );
        fechaNac.addChangeListener(
                new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        Calendar fecha = Calendar.getInstance();//Usada como auxiliar
                        fecha.setTime((Date) fechaNac.getValue()); //Se le asigna la fecha del
                        edad = fechaa.get(Calendar.YEAR) - fecha.get(Calendar.YEAR);
                        //Se le asgian el valor de la edad
                        if(fecha.get(Calendar.MONTH) < fechaa.get(Calendar.MONTH))
                            edad--;
                        else
                            if(fecha.get(Calendar.MONTH) == fechaa.get(Calendar.MONTH) &&
                                fecha.get(Calendar.DATE) < fechaa.get(Calendar.DATE))
                                edad--;
                        if(edad < MIN_EDAD){
                            //Has que fechaNac tome el valor adecuado
                            fechaNac.repaint();
                        }
                        else{
                            tAnios.setText("Años: "+edad);
                        }
                    }
                }
        );
        nombre.setActionCommand("nombre");
        peso.setActionCommand("peso");

        nombre.addActionListener(admonAccion);
        nombre.addFocusListener(admonFocus);
        nombre.addMouseListener(admonMouse);
        peso.addActionListener(admonAccion);
        peso.addFocusListener(admonFocus);
        peso.addMouseListener(admonMouse);

        return camposPanel;

    }
    public ImageIcon imagenActividad(int a){
        String ruta = "/imagenes/";
        String nombreImagen[]={"ligera.jpg","moderada.jpg","muyactiva.jpg","sedentario.jpg"};
        URL url = getClass().getResource(ruta+nombreImagen[a]);

        Image reescalado = (new ImageIcon(url).getImage()).getScaledInstance(100,93,Image.SCALE_SMOOTH);
        return new ImageIcon(reescalado);
    }
    public String textoActividad(int a){
        String descp[]={"No realizan prácticamente nada de ejercicio",
                        "Realizan ejercicios suaves de 1 a 3 veces por semana",
                        "Practican deporte de 3 a 5 veces por semana",
                        "Practican deporte de 6 a 7 días por semana"};
        String str = descp[a];
        return str;
    }
    public JPanel crearSelecionActividad(){
        JPanel seleccion = new JPanel();
        JPanel pActiv = new JPanel();
        JPanel pDesAct = new JPanel();
        seleccion.setLayout(new BorderLayout());
        //7e
        actividad = new JComboBox(ACTIVIDADES);
        descripcionActividad = new JLabel(textoActividad(0),imagenActividad(0),SwingConstants.CENTER);
        //7f
        descripcionActividad.setHorizontalTextPosition(JLabel.CENTER);
        descripcionActividad.setVerticalTextPosition(JLabel.NORTH);
        //ii)
        pActiv.add(actividad);
        pDesAct.add(descripcionActividad);
        //iii
        pActiv.setBorder(
            BorderFactory.createCompoundBorder(
                    BorderFactory.createTitledBorder("Selecciona la actividad"),
                    BorderFactory.createEmptyBorder(5,5,5,5)
            )
        );
        pDesAct.setBorder(
            BorderFactory.createCompoundBorder(
                    BorderFactory.createTitledBorder(""),
                    BorderFactory.createEmptyBorder(5,5,5,5)
            )
        );
        seleccion.add(pActiv,BorderLayout.NORTH);
        seleccion.add(pDesAct,BorderLayout.SOUTH);
        return seleccion;
    }

    private class AdmonEvenTeclado extends KeyAdapter{

        @Override
        public void keyTyped(KeyEvent ke){
            char k = ke.getKeyChar();
            JTextField productor = (JTextField) ke.getSource();
            if(productor == nombre){
                if(nombre.getSelectedText()!=null)
                    nombre.setText("");
                if(!Character.isAlphabetic(k) && k!=KeyEvent.VK_SPACE)
                    ke.consume();
            }
            if(productor == peso){
                //Aqui solo deben aceptar digitos
                if(!Character.isDigit(ke.getKeyChar())){
                    ke.consume();
                }
            }
        }
    }
    private class AdmonFocus extends FocusAdapter{
        @Override
        public void focusGained(FocusEvent avt){
            Object o = avt.getSource();
            if(o instanceof JTextField){
                JTextField txt = (javax.swing.JTextField) o;
                //Has que el texto de txt se seleccione usando los métodos
                txt.setSelectionStart(0);
                txt.setSelectionEnd(txt.getText().length());
            }

        }
    }
    private class AdmonMouseAdap extends MouseAdapter{
        @Override
        public void mousePressed(MouseEvent map){
            if(map.getSource() == nombre && nombre.getCaretPosition() == 0){
                nombre.setText("");
            }
            if(map.getSource() == peso && peso.getCaretPosition() == 0){
                peso.setText("");
            }
        }
    }
    private class AdmonAccion implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae){
            switch(ae.getActionCommand()){
                case "nombre":
                    validaNombre = false;
                    if(nombre.getText() !="" && !nombre.getText().equals(DA_NOMBRE)){
                        validaNombre = true;
                        fechaNac.requestFocus();
                    }
                    else
                    nombre.requestFocus();
                    break;
                case "peso":
                    int vPeso = 0;
                    validaPeso = false;
                    if(peso.getText()!="" && !peso.getText().equals(DA_PESO)){
                        if(Integer.parseInt(peso.getText()) < 40 || Integer.parseInt(peso.getText()) > 120){
                            validaPeso = true;
                            JOptionPane.showMessageDialog(null,"Peso fuera de los limtes (mayor a 40 kgs y menor a 120 kgs)");
                            peso.requestFocus();
                        }
                        else{
                            peso.requestFocus();
                        }
                    }
                    break;

            }
        }
    }

    public static void main(String args[]){
        CaloriasRequeridas calo = new CaloriasRequeridas();
    }
}
