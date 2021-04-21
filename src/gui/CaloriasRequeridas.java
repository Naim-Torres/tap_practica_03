package gui;

import javax.swing.*;
import java.awt.*;
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
        //datosC.add(crearSelecionActividad(), BorderLayout.EAST);
        base.add(titulo, BorderLayout.NORTH);
        base.add(datosC, BorderLayout.CENTER);
        //el método formarPanelResultados() integra la segunda seccion de la GUI
        //base.add(formarPanelResultados(),BorderLayout.SOUTH);
        this.setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(700, 450);

    }
    public JPanel formarPanelCampos(){
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
        JSpinner.DateEditor fmda = new JSpinner.DateEditor(fechaNac,"yyyy.mm.dd");
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
        return camposPanel;

    }
    public static void main(String args[]){
        CaloriasRequeridas calo = new CaloriasRequeridas();
    }
}
