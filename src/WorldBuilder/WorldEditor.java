/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package WorldBuilder;
import zZz.MapTXT;
import Graphics.Map3D;
import BDD.DerbySalvar;
import Main.Myrran;
import Geo.Terreno;
import DATA.MiscData;
import Geo.Estatico;
import Geo.Mapa;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;



/**
 *
 * @author Hanto
 */
public final class WorldEditor extends javax.swing.JFrame 
{

    private static int numTerreno;
    private static int numEstatico1;
    private static int numMapa;
    private static int numLlave=0;
    
    private static int brochaIDEstatico=-1;
    private static int brochaIDTerreno=-1;
    private static int brochaIDCursor=0;
    
    private static JFrame fterreno = new JFrame ("Terrenos");
    private static JToolBar tbterreno = new JToolBar(); 
    private static JFrame fcursores = new JFrame ("Cursores");
    private static JToolBar tbcursores = new JToolBar();
    
    private static SelectColor colorUI = new SelectColor();
    private static SelectFile fileUI = new SelectFile();
    private static MapTXT mapUI = new MapTXT ();
    
    public static int getnumTerreno()           {return numTerreno;}
    public static int getnumEstatico1()         {return numEstatico1;}
    public static int getnumMapa()              {return numMapa;}
    public static int getnumLlave()             {return numLlave;}
    
    public static void setnumTerreno (int i)        { numTerreno = i; }
    public static void setbrochaIDTerreno (int i)   { brochaIDTerreno = i; }
    public static void setbrochaIDCursor( int i )   { brochaIDCursor = i; }
    public static int getbrochaIDEstatico()         { return brochaIDEstatico; }
    public static int getbrochaIDTerreno()          { return brochaIDTerreno;}
    public static int getbrochaIDCursor()           { return brochaIDCursor; }
    public static MapTXT getmap()                   { return mapUI;}
    
    
    public WorldEditor() 
    {
        initComponents();
        ActualizarListadeTerrenos ();
        ActualizarToolbarTerrenos ();
        ActualizarListadeMapas ();
        ActualizarDatosTerreno ();
        ActualizarToolbarCursores();
        
        ActualizarListadeEstaticos1();
        ActualizarDatosEstatico1 ();
        
        LMapas.setSelectedIndex(0);
        LTerrenos.setSelectedIndex(0);
        LEstaticos1.setSelectedIndex(0);
        
        LMapas.setFixedCellHeight(12);
        LTerrenos.setFixedCellHeight(12);
        LEstaticos1.setFixedCellHeight(12);
    }

    public static void ActualizarListadeTerrenos ()
    {
        DefaultListModel dl = new DefaultListModel ();
        for (int i=0; i<Myrran.getMundo().ListaTerrenos().size();i++)
        {dl.addElement(Myrran.getMundo().ListaTerrenos().get(i).getNombre());}
        
        LTerrenos.setModel(dl);  
    }
    
    public static void ActualizarListadeEstaticos1 ()
    {
        DefaultListModel dl = new DefaultListModel ();
        for (int i=0; i<Myrran.getMundo().ListaEstaticos().size();i++)
        {dl.addElement(Myrran.getMundo().ListaEstaticos().get(i).getNombre());}
        
        LEstaticos1.setModel(dl);
    }
   
    public static void ActualizarDatosEstatico1 ()
    {
        try 
        {
            TFEstatico1ID.setText(Integer.toString(Myrran.getMundo().ListaEstaticos().get(numEstatico1).getID()));
            TFEstatico1Nombre.setText(Myrran.getMundo().ListaEstaticos().get(numEstatico1).getNombre());
            CBEstatico1Muro.setSelected(Myrran.getMundo().ListaEstaticos().get(numEstatico1).isSolido());
            if (Myrran.getMundo().ListaEstaticos().get(numEstatico1).getIMG() != null) 
                {BEstatico1Bitmap.setIcon(new ImageIcon (Myrran.getMundo().ListaEstaticos().get(numEstatico1).getIMG().getScaledInstance(64, 64, 1)));}
            else {BEstatico1Bitmap.setIcon(new ImageIcon (MiscData.TERRENO_DIRECCIONBITMAP_NEW));}
            BEstatico1Bitmap.setBackground(new Color(255,255,204));
        } catch (Exception e) {}
        
    }
    
    public static void ActualizarDatosTerreno ()
    {
        try {
        TFID.setText(Integer.toString(Myrran.getMundo().ListaTerrenos().get(numTerreno).getID()));
        TFNombre.setText(Myrran.getMundo().ListaTerrenos().get(numTerreno).getNombre());
        BColor.setBackground(Myrran.getMundo().ListaTerrenos().get(numTerreno).getColorTerreno());
        CBMuro.setSelected(Myrran.getMundo().ListaTerrenos().get(numTerreno).getFlagMuro());
        CBPuerta.setSelected(Myrran.getMundo().ListaTerrenos().get(numTerreno).getFlagPuerta());
        if (Myrran.getMundo().ListaTerrenos().get(numTerreno).getIMGFile() != null) 
            {BTerrenoBitmap.setIcon(new ImageIcon (Myrran.getMundo().ListaTerrenos().get(numTerreno).getIMG().getScaledInstance(64, 64, 1)));}
        else {BTerrenoBitmap.setIcon(new ImageIcon (MiscData.TERRENO_DIRECCIONBITMAP_NEW));}
        BTerrenoBitmap.setBackground(new Color(255,255,204));
        ActualizarListaLlaves();
        } catch (Exception e) {}
        
    }
    
    public static void ActualizarListadeMapas ()
    {
        DefaultListModel dl = new DefaultListModel ();
        for (int i=0;i<Myrran.getMundo().ListaMapas().size();i++)
        {dl.addElement(Myrran.getMundo().ListaMapas().get(i).getNombre());}
          
        LMapas.setModel(dl);
    }
    
    public static void ActualizarListaLlaves ()
    {
        DefaultListModel dl = new DefaultListModel ();
        for (int i=0;i<Myrran.getMundo().ListaTerrenos().get(numTerreno).getLlaves().size();i++)
        {dl.addElement(Myrran.getMundo().ListaTerrenos().get(numTerreno).getLlaves().get(i));}
          
        LLlaves.setModel(dl);
        
        numLlave=0;
        LLlaves.setSelectedIndex(numLlave);
        
        try { TFLlaves.setText(Myrran.getMundo().ListaTerrenos().get(numTerreno).getLlaves().get(numLlave).toString());}
        catch (Exception e) {TFLlaves.setText("");}
    }
    
    public static void ActualizarToolbarCursores ()
    {
        tbcursores.removeAll();
        for (int i=0; i<Myrran.getMundo().ListaPlantillas().size();i++)
        {   
            
            JButton button = new JButton();
            String targetFilename = MiscData.MAP3D_TARGET_FILENAME;
            switch (i)
            {    
                case 0: {targetFilename = MiscData.CURSOR_DIRECCIONBITMAP_CURSOR001;break;}
                case 1: {targetFilename = MiscData.CURSOR_DIRECCIONBITMAP_CURSOR002;break;}
                case 2: {targetFilename = MiscData.CURSOR_DIRECCIONBITMAP_CURSOR003;break;}
                case 3: {targetFilename = MiscData.CURSOR_DIRECCIONBITMAP_CURSOR004;break;}
                case 4: {targetFilename = MiscData.CURSOR_DIRECCIONBITMAP_CURSOR005;break;}
            }
            
            Image imga;
            
            URL url = WorldEditor.class.getClassLoader().getResource(targetFilename);
            if (url == null) {System.out.println (targetFilename+" no se encuentra.");}
            else 
            {       
                try 
                {
                    imga = ImageIO.read(url);
                    ImageIcon ic = new ImageIcon(imga);
                    button.setIcon(ic);
                }
                catch (Exception e) {}
            }
            button.setFocusable(false);
            
            final int id = Myrran.getMundo().ListaPlantillas().get(i).getID();
            
            button.addActionListener(new ActionListener() 
            {   public void actionPerformed (ActionEvent evt) 
                {
                    brochaIDCursor=id;
                    Map3D.cursor.setForma(Myrran.getMundo().ListaPlantillas().get(brochaIDCursor).getForma());
                    Map3D.cursor.setPuntero(Myrran.getMundo().ListaPlantillas().get(brochaIDCursor).getX(), Myrran.getMundo().ListaPlantillas().get(brochaIDCursor).getY());
                }
            });    
            
            tbcursores.add(button);
        }
        fcursores.add(tbcursores);
        tbcursores.setFloatable(false);
        fcursores.setAlwaysOnTop(true);
        fcursores.setResizable(false);
        fcursores.pack();
    }
    
    public static void ActualizarToolbarTerrenos ()
    {   
        tbterreno.removeAll();
        for (int i=0; i<Myrran.getMundo().ListaTerrenos().size();i++)
        {
            ImageIcon ic;
            
            if (Myrran.getMundo().ListaTerrenos().get(i).getIMGFile() == null)
                {ic = new ImageIcon(MiscData.TERRENO_DIRECCIONBITMAP_NEW);}
            else {ic = new ImageIcon(Myrran.getMundo().ListaTerrenos().get(i).getIMGFile());}
            
            //Si no hay un FLUSH lo coge de cache y el hijo de puta no lo actualiza, 5 horas perdidas con esta puta mierda!
            ic.getImage().flush();
       
            JButton button = new JButton(ic);
           
            button.setForeground(Myrran.getMundo().ListaTerrenos().get(i).getColorTerreno());
            button.setFont(new java.awt.Font("Consolas", Font.PLAIN, 20));
            button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            button.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
            button.setFocusable(false);
            
            final int id = Myrran.getMundo().ListaTerrenos().get(i).getID();
            
            button.addActionListener(new ActionListener() 
            {   public void actionPerformed (ActionEvent evt) 
                {
                    brochaIDTerreno=id;
                    seleccionarTerrenobyID (brochaIDTerreno);
                }
            });
            tbterreno.add(button);
        }
        fterreno.add(tbterreno);
        tbterreno.setFloatable(Boolean.FALSE);
        fterreno.setAlwaysOnTop(Boolean.TRUE);
        fterreno.setResizable(Boolean.FALSE);
        fterreno.pack();
    }
    
    public static void seleccionarTerrenobyID (int ID)
    {
        for (int i=0; i<Myrran.getMundo().ListaTerrenos().size();i++)
        {
            if (Myrran.getMundo().ListaTerrenos().get(i).getID() == brochaIDTerreno) {LTerrenos.setSelectedIndex(i);break;}
        }
        ActualizarDatosTerreno();
    }        
    
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form WorldEditor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        SMapas = new javax.swing.JScrollPane();
        LMapas = new javax.swing.JList();
        BBorrarMapa = new javax.swing.JButton();
        BAñadirMapa = new javax.swing.JButton();
        STerrenos = new javax.swing.JScrollPane();
        LTerrenos = new javax.swing.JList();
        BAñadirTerreno = new javax.swing.JButton();
        BBorrarTerreno = new javax.swing.JButton();
        TFID = new javax.swing.JTextField();
        TFNombre = new javax.swing.JTextField();
        BColor = new javax.swing.JButton();
        CBMuro = new javax.swing.JCheckBox();
        CBPuerta = new javax.swing.JCheckBox();
        TFLlaves = new javax.swing.JTextField();
        SLLaves = new javax.swing.JScrollPane();
        LLlaves = new javax.swing.JList();
        BAñadirLlaves = new javax.swing.JButton();
        BBorrarLlaves = new javax.swing.JButton();
        BTerrenoBitmap = new javax.swing.JButton(new ImageIcon (DATA.MiscData.TERRENO_DIRECCIONBITMAP_NEW));
        SCeldas1 = new javax.swing.JScrollPane();
        LEstaticos1 = new javax.swing.JList();
        BAñadirEstatico1 = new javax.swing.JButton();
        BBorrarEstatico1 = new javax.swing.JButton();
        BEstatico1Bitmap = new javax.swing.JButton(new ImageIcon (DATA.MiscData.TERRENO_DIRECCIONBITMAP_NEW));
        TFEstatico1ID = new javax.swing.JTextField();
        TFEstatico1Nombre = new javax.swing.JTextField();
        CBEstatico1Muro = new javax.swing.JCheckBox();
        MMenu = new javax.swing.JMenuBar();
        MFile = new javax.swing.JMenu();
        MSalvar = new javax.swing.JMenuItem();
        MVer = new javax.swing.JMenu();
        RBMTiposTerreno = new javax.swing.JRadioButtonMenuItem();
        RBMTiposCursor = new javax.swing.JRadioButtonMenuItem();
        RBMMapa = new javax.swing.JRadioButtonMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Myrran Terrain Editor");

        LMapas.setFont(new java.awt.Font("Consolas", 1, 11)); // NOI18N
        LMapas.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        LMapas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        LMapas.setSelectionBackground(new java.awt.Color(255, 204, 51));
        LMapas.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                LMapasValueChanged(evt);
            }
        });
        SMapas.setViewportView(LMapas);

        BBorrarMapa.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        BBorrarMapa.setText("-");
        BBorrarMapa.setAlignmentY(0.0F);
        BBorrarMapa.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        BBorrarMapa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BBorrarMapaActionPerformed(evt);
            }
        });

        BAñadirMapa.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        BAñadirMapa.setText("+");
        BAñadirMapa.setAlignmentY(0.0F);
        BAñadirMapa.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        BAñadirMapa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAñadirMapaActionPerformed(evt);
            }
        });

        LTerrenos.setFont(new java.awt.Font("Consolas", 1, 11)); // NOI18N
        LTerrenos.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        LTerrenos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        LTerrenos.setSelectionBackground(new java.awt.Color(255, 204, 51));
        LTerrenos.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                LTerrenosValueChanged(evt);
            }
        });
        STerrenos.setViewportView(LTerrenos);

        BAñadirTerreno.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        BAñadirTerreno.setText("+");
        BAñadirTerreno.setAlignmentY(0.0F);
        BAñadirTerreno.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        BAñadirTerreno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAñadirTerrenoActionPerformed(evt);
            }
        });

        BBorrarTerreno.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        BBorrarTerreno.setText("-");
        BBorrarTerreno.setAlignmentY(0.0F);
        BBorrarTerreno.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        BBorrarTerreno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BBorrarTerrenoActionPerformed(evt);
            }
        });

        TFID.setEditable(false);
        TFID.setFont(new java.awt.Font("Consolas", 0, 11)); // NOI18N
        TFID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        TFID.setText("ID");
        TFID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TFIDKeyPressed(evt);
            }
        });

        TFNombre.setFont(new java.awt.Font("Consolas", 0, 11)); // NOI18N
        TFNombre.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        TFNombre.setText("Nombre");
        TFNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TFNombreKeyPressed(evt);
            }
        });

        BColor.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        BColor.setText("Color");
        BColor.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        BColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BColorActionPerformed(evt);
            }
        });

        CBMuro.setFont(new java.awt.Font("Arial", 0, 9)); // NOI18N
        CBMuro.setText("Muro");
        CBMuro.setAlignmentY(0.0F);
        CBMuro.setMaximumSize(new java.awt.Dimension(45, 15));
        CBMuro.setMinimumSize(new java.awt.Dimension(45, 15));
        CBMuro.setPreferredSize(new java.awt.Dimension(45, 15));
        CBMuro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CBMuroActionPerformed(evt);
            }
        });

        CBPuerta.setFont(new java.awt.Font("Arial", 0, 9)); // NOI18N
        CBPuerta.setText("Puerta");
        CBPuerta.setAlignmentY(0.0F);
        CBPuerta.setMaximumSize(new java.awt.Dimension(53, 15));
        CBPuerta.setMinimumSize(new java.awt.Dimension(53, 15));
        CBPuerta.setPreferredSize(new java.awt.Dimension(53, 15));
        CBPuerta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CBPuertaActionPerformed(evt);
            }
        });

        TFLlaves.setFont(new java.awt.Font("Consolas", 0, 10)); // NOI18N
        TFLlaves.setText("jTextField1");
        TFLlaves.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TFLlavesKeyPressed(evt);
            }
        });

        SLLaves.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        LLlaves.setFont(new java.awt.Font("Consolas", 0, 10)); // NOI18N
        LLlaves.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        LLlaves.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        LLlaves.setSelectionBackground(new java.awt.Color(255, 204, 51));
        LLlaves.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                LLlavesValueChanged(evt);
            }
        });
        SLLaves.setViewportView(LLlaves);

        BAñadirLlaves.setFont(new java.awt.Font("Arial", 0, 9)); // NOI18N
        BAñadirLlaves.setText("+");
        BAñadirLlaves.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        BAñadirLlaves.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAñadirLlavesActionPerformed(evt);
            }
        });

        BBorrarLlaves.setFont(new java.awt.Font("Arial", 0, 9)); // NOI18N
        BBorrarLlaves.setText("-");
        BBorrarLlaves.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        BBorrarLlaves.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BBorrarLlavesActionPerformed(evt);
            }
        });

        BTerrenoBitmap.setBackground(new java.awt.Color(255, 255, 204));
        BTerrenoBitmap.setText("");
        BTerrenoBitmap.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        BTerrenoBitmap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTerrenoBitmapActionPerformed(evt);
            }
        });

        LEstaticos1.setFont(new java.awt.Font("Consolas", 1, 11)); // NOI18N
        LEstaticos1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        LEstaticos1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        LEstaticos1.setSelectionBackground(new java.awt.Color(255, 204, 51));
        LEstaticos1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                LEstaticos1ValueChanged(evt);
            }
        });
        SCeldas1.setViewportView(LEstaticos1);

        BAñadirEstatico1.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        BAñadirEstatico1.setText("+");
        BAñadirEstatico1.setAlignmentY(0.0F);
        BAñadirEstatico1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        BAñadirEstatico1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAñadirEstatico1ActionPerformed(evt);
            }
        });

        BBorrarEstatico1.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        BBorrarEstatico1.setText("-");
        BBorrarEstatico1.setAlignmentY(0.0F);
        BBorrarEstatico1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        BBorrarEstatico1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BBorrarEstatico1ActionPerformed(evt);
            }
        });

        BEstatico1Bitmap.setBackground(new java.awt.Color(255, 255, 204));
        BEstatico1Bitmap.setText("");
        BEstatico1Bitmap.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        BEstatico1Bitmap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BEstatico1BitmapActionPerformed(evt);
            }
        });

        TFEstatico1ID.setEditable(false);
        TFEstatico1ID.setFont(new java.awt.Font("Consolas", 0, 11)); // NOI18N
        TFEstatico1ID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        TFEstatico1ID.setText("ID");

        TFEstatico1Nombre.setFont(new java.awt.Font("Consolas", 0, 11)); // NOI18N
        TFEstatico1Nombre.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        TFEstatico1Nombre.setText("Nombre");
        TFEstatico1Nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TFEstatico1NombreKeyPressed(evt);
            }
        });

        CBEstatico1Muro.setFont(new java.awt.Font("Arial", 0, 9)); // NOI18N
        CBEstatico1Muro.setText("Muro");
        CBEstatico1Muro.setAlignmentY(0.0F);
        CBEstatico1Muro.setMaximumSize(new java.awt.Dimension(45, 15));
        CBEstatico1Muro.setMinimumSize(new java.awt.Dimension(45, 15));
        CBEstatico1Muro.setPreferredSize(new java.awt.Dimension(45, 15));
        CBEstatico1Muro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CBEstatico1MuroActionPerformed(evt);
            }
        });

        MFile.setText("File");

        MSalvar.setText("Salvar Cambios");
        MSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MSalvarActionPerformed(evt);
            }
        });
        MFile.add(MSalvar);

        MMenu.add(MFile);

        MVer.setText("Ver");

        RBMTiposTerreno.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        RBMTiposTerreno.setText("Tipos Terreno");
        RBMTiposTerreno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RBMTiposTerrenoActionPerformed(evt);
            }
        });
        MVer.add(RBMTiposTerreno);

        RBMTiposCursor.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        RBMTiposCursor.setText("Tipos Cursor");
        RBMTiposCursor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RBMTiposCursorActionPerformed(evt);
            }
        });
        MVer.add(RBMTiposCursor);

        RBMMapa.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        RBMMapa.setText("Mapa");
        RBMMapa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RBMMapaActionPerformed(evt);
            }
        });
        MVer.add(RBMMapa);

        MMenu.add(MVer);

        setJMenuBar(MMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(BBorrarMapa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BAñadirMapa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(SMapas, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(STerrenos, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(BBorrarTerreno, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BAñadirTerreno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(TFID, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                                .addComponent(TFNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                                .addComponent(BTerrenoBitmap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(BColor, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(BBorrarEstatico1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BAñadirEstatico1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(SCeldas1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BEstatico1Bitmap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(TFEstatico1ID)
                            .addComponent(TFEstatico1Nombre))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(BBorrarLlaves, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BAñadirLlaves, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(CBPuerta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CBMuro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(SLLaves, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                        .addComponent(TFLlaves, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(CBEstatico1Muro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(CBMuro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CBPuerta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TFLlaves, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(SLLaves, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(TFID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TFNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BTerrenoBitmap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(SMapas, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(STerrenos, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BBorrarMapa)
                    .addComponent(BAñadirMapa)
                    .addComponent(BColor)
                    .addComponent(BBorrarTerreno)
                    .addComponent(BAñadirTerreno)
                    .addComponent(BBorrarLlaves)
                    .addComponent(BAñadirLlaves))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TFEstatico1ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CBEstatico1Muro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TFEstatico1Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BEstatico1Bitmap, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(SCeldas1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BAñadirEstatico1)
                    .addComponent(BBorrarEstatico1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BAñadirTerrenoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAñadirTerrenoActionPerformed
        Terreno terreno = new Terreno ();
        terreno.nuevoTipoTerreno();
        Myrran.getMundo().ListaTerrenos().add(terreno);
        ActualizarListadeTerrenos();
        LTerrenos.setSelectedIndex(Myrran.getMundo().ListaTerrenos().size()-1);
        
        ActualizarToolbarTerrenos ();
    }//GEN-LAST:event_BAñadirTerrenoActionPerformed

    private void BBorrarTerrenoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BBorrarTerrenoActionPerformed
        //No se puede borrar un tipo de terreno, si sus datos estan en cualquier mapa
        if (Myrran.getMundo().BuscarTerrenoEnTodosLosMapas(numTerreno)) return;
        //en caso negativo intentamso borrarlo
        try { Myrran.getMundo().ListaTerrenos().remove(numTerreno);}
        catch (Exception e) {};
        //despues de actualizar la lista el puntero siempre se coloca en la posicion -1, por tanto salvamos su posicion actual en la variable auxilar AUX
        int aux = numTerreno-1;
        ActualizarListadeTerrenos();
        if (aux>=0) {LTerrenos.setSelectedIndex(aux);}
        else {LTerrenos.setSelectedIndex(0);}
        
        ActualizarToolbarTerrenos ();
    }//GEN-LAST:event_BBorrarTerrenoActionPerformed

    private void LTerrenosValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_LTerrenosValueChanged
        if (LTerrenos.getSelectedIndex() >= 0) {numTerreno = LTerrenos.getSelectedIndex();brochaIDTerreno = Myrran.getMundo().ListaTerrenos().get(numTerreno).getID();}
        else {numTerreno = 0;}
       
        ActualizarDatosTerreno ();
    }//GEN-LAST:event_LTerrenosValueChanged

    private void TFIDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TFIDKeyPressed
        if (evt.getKeyCode()==10)
        {
            try {Myrran.getMundo().ListaTerrenos().get(numTerreno).setID(Integer.parseInt(TFID.getText()));}
            catch (Exception e) {TFID.setText(Integer.toString(Myrran.getMundo().ListaTerrenos().get(numTerreno).getID()));}
            TFID.setText(Integer.toString(Myrran.getMundo().ListaTerrenos().get(numTerreno).getID()));
        }
        else return;
    }//GEN-LAST:event_TFIDKeyPressed

    private void BColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BColorActionPerformed

       colorUI.setLocation(MouseInfo.getPointerInfo().getLocation());
       colorUI.setVisible(true);
    }//GEN-LAST:event_BColorActionPerformed

    private void BAñadirMapaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAñadirMapaActionPerformed
        Mapa mapa = new Mapa ();
        Myrran.getMundo().ListaMapas().add(mapa);
        ActualizarListadeMapas();
        LMapas.setSelectedIndex(Myrran.getMundo().ListaMapas().size()-1);
    }//GEN-LAST:event_BAñadirMapaActionPerformed

    private void BBorrarMapaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BBorrarMapaActionPerformed
        try { Myrran.getMundo().ListaMapas().remove(numMapa);}
        catch (Exception e) {};
        
        int aux = numMapa-1;
        ActualizarListadeMapas();
        if (aux>=0) {LMapas.setSelectedIndex(aux);}
        else {LMapas.setSelectedIndex(0);}
    }//GEN-LAST:event_BBorrarMapaActionPerformed

    private void LMapasValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_LMapasValueChanged
        if (LMapas.getSelectedIndex() >= 0) 
        {
            numMapa = LMapas.getSelectedIndex();
            if (mapUI.isVisible()) mapUI.printMap();
        }
        else {numMapa = 0;}
    }//GEN-LAST:event_LMapasValueChanged

    private void CBMuroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CBMuroActionPerformed
        try 
        {
            if (CBMuro.isSelected()) {Myrran.getMundo().ListaTerrenos().get(numTerreno).setFlagMuro(Boolean.TRUE);mapUI.printMap();}
            else {Myrran.getMundo().ListaTerrenos().get(numTerreno).setFlagMuro(Boolean.FALSE);mapUI.printMap();}
        } catch (Exception e) {}
    }//GEN-LAST:event_CBMuroActionPerformed

    private void MSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MSalvarActionPerformed
       Thread thread = new Thread (new DerbySalvar());
       thread.start();
    }//GEN-LAST:event_MSalvarActionPerformed

    private void RBMTiposTerrenoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RBMTiposTerrenoActionPerformed
        if (fterreno.isVisible()) fterreno.setVisible(Boolean.FALSE);
        else fterreno.setVisible(Boolean.TRUE);
    }//GEN-LAST:event_RBMTiposTerrenoActionPerformed

    private void RBMMapaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RBMMapaActionPerformed
        if (mapUI.isVisible()) mapUI.setVisible(Boolean.FALSE);
        else 
        {
            mapUI.setVisible(true);
            try {mapUI.printMap();}
            catch (Exception e) {;}
        }
    }//GEN-LAST:event_RBMMapaActionPerformed

    private void CBPuertaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CBPuertaActionPerformed
        try 
        {
            if (CBPuerta.isSelected()) {Myrran.getMundo().ListaTerrenos().get(numTerreno).setFlagPuerta(Boolean.TRUE);mapUI.printMap();}
            else {Myrran.getMundo().ListaTerrenos().get(numTerreno).setFlagPuerta(Boolean.FALSE);mapUI.printMap();}
        } catch (Exception e) {}
    }//GEN-LAST:event_CBPuertaActionPerformed

    private void BAñadirLlavesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAñadirLlavesActionPerformed
        Myrran.getMundo().ListaTerrenos().get(numTerreno).getLlaves().add(-1);
        ActualizarListaLlaves();
        LLlaves.setSelectedIndex(Myrran.getMundo().ListaTerrenos().get(numTerreno).getLlaves().size()-1);
    }//GEN-LAST:event_BAñadirLlavesActionPerformed

    private void LLlavesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_LLlavesValueChanged
        if (LLlaves.getSelectedIndex() >= 0)       
        {
            numLlave = LLlaves.getSelectedIndex();
            TFLlaves.setText(Myrran.getMundo().ListaTerrenos().get(numTerreno).getLlaves().get(numLlave).toString());
        }
        else {numMapa = 0;}
    }//GEN-LAST:event_LLlavesValueChanged

    private void TFLlavesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TFLlavesKeyPressed
        if (evt.getKeyCode()==10)
        {
            try 
            {   Integer llave = Integer.parseInt(TFLlaves.getText());
                Myrran.getMundo().ListaTerrenos().get(numTerreno).getLlaves().set(numLlave, llave);
            }
            catch (Exception e) {}
            int aux = numLlave;
            ActualizarListaLlaves ();
            LLlaves.setSelectedIndex(aux);
        }
        else return;
    }//GEN-LAST:event_TFLlavesKeyPressed

    private void BBorrarLlavesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BBorrarLlavesActionPerformed
        try { Myrran.getMundo().ListaTerrenos().get(numTerreno).getLlaves().remove(numLlave);}
        catch (Exception e) {}
        
        int aux = numLlave-1;
        ActualizarListaLlaves();
        if (aux>=0) {LLlaves.setSelectedIndex(aux);}
        else {LLlaves.setSelectedIndex(0);}
        
    }//GEN-LAST:event_BBorrarLlavesActionPerformed

    private void BTerrenoBitmapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTerrenoBitmapActionPerformed
        fileUI.isTerreno();
        fileUI.setVisible(true);
    }//GEN-LAST:event_BTerrenoBitmapActionPerformed

    private void RBMTiposCursorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RBMTiposCursorActionPerformed
        if (fcursores.isVisible()) fcursores.setVisible(Boolean.FALSE);
        else fcursores.setVisible(Boolean.TRUE);
    }//GEN-LAST:event_RBMTiposCursorActionPerformed

    private void LEstaticos1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_LEstaticos1ValueChanged
        if (LEstaticos1.getSelectedIndex() >= 0) {numEstatico1 = LEstaticos1.getSelectedIndex(); brochaIDEstatico = Myrran.getMundo().ListaEstaticos().get(numEstatico1).getID();}
        else {numEstatico1 = 0;}
        
        ActualizarDatosEstatico1 ();
    }//GEN-LAST:event_LEstaticos1ValueChanged

    private void BAñadirEstatico1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAñadirEstatico1ActionPerformed
        Estatico estatico = new Estatico ();
        estatico.nuevoTipoEstatico();
        Myrran.getMundo().ListaEstaticos().add(estatico);
        ActualizarListadeEstaticos1();
        LEstaticos1.setSelectedIndex(Myrran.getMundo().ListaEstaticos().size()-1);
        
        ActualizarToolbarTerrenos ();
    }//GEN-LAST:event_BAñadirEstatico1ActionPerformed

    private void BEstatico1BitmapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BEstatico1BitmapActionPerformed
        fileUI.isEstatico1();
        fileUI.setVisible(true);
    }//GEN-LAST:event_BEstatico1BitmapActionPerformed

    private void TFNombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TFNombreKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            try {Myrran.getMundo().ListaTerrenos().get(numTerreno).setNombre(TFNombre.getText());}
            catch (Exception e) {TFNombre.setText(Myrran.getMundo().ListaTerrenos().get(numTerreno).getNombre());}
            TFNombre.setText(Myrran.getMundo().ListaTerrenos().get(numTerreno).getNombre());
            int aux = numTerreno;
            ActualizarListadeTerrenos ();
            LTerrenos.setSelectedIndex(aux);
        }
    }//GEN-LAST:event_TFNombreKeyPressed

    private void TFEstatico1NombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TFEstatico1NombreKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            try {Myrran.getMundo().ListaEstaticos().get(numEstatico1).setNombre(TFEstatico1Nombre.getText());}
            catch (Exception e) {TFEstatico1Nombre.setText(Myrran.getMundo().ListaEstaticos().get(numEstatico1).getNombre());}
            TFEstatico1Nombre.setText(Myrran.getMundo().ListaEstaticos().get(numEstatico1).getNombre());
            int aux = numEstatico1;
            ActualizarListadeEstaticos1 ();
            LEstaticos1.setSelectedIndex(aux);
        }
    }//GEN-LAST:event_TFEstatico1NombreKeyPressed

    private void BBorrarEstatico1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BBorrarEstatico1ActionPerformed
        //No se puede borrar un tipo de Estatico, si sus datos estan en cualquier mapa
        if (Myrran.getMundo().BuscarEstaticoEnTodosLosMapas(numEstatico1)) return;
        //en caso negativo intentamso borrarlo
        try { Myrran.getMundo().ListaEstaticos().remove(numEstatico1);}
        catch (Exception e) {};
        //despues de actualizar la lista el puntero siempre se coloca en la posicion -1, por tanto salvamos su posicion actual en la variable auxilar AUX
        int aux = numEstatico1-1;
        ActualizarListadeEstaticos1();
        if (aux>=0) {LEstaticos1.setSelectedIndex(aux);}
        else {LEstaticos1.setSelectedIndex(0);}
    }//GEN-LAST:event_BBorrarEstatico1ActionPerformed

    private void CBEstatico1MuroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CBEstatico1MuroActionPerformed
        try 
        {
            if (CBEstatico1Muro.isSelected()) {Myrran.getMundo().ListaEstaticos().get(numEstatico1).isSolido(Boolean.TRUE);}
            else {Myrran.getMundo().ListaEstaticos().get(numEstatico1).isSolido(Boolean.FALSE);}
        } catch (Exception e) {}
    }//GEN-LAST:event_CBEstatico1MuroActionPerformed

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
            java.util.logging.Logger.getLogger(WorldEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WorldEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WorldEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WorldEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WorldEditor().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BAñadirEstatico1;
    private javax.swing.JButton BAñadirLlaves;
    private static javax.swing.JButton BAñadirMapa;
    private javax.swing.JButton BAñadirTerreno;
    private javax.swing.JButton BBorrarEstatico1;
    private javax.swing.JButton BBorrarLlaves;
    private static javax.swing.JButton BBorrarMapa;
    private javax.swing.JButton BBorrarTerreno;
    private static javax.swing.JButton BColor;
    private static javax.swing.JButton BEstatico1Bitmap;
    private static javax.swing.JButton BTerrenoBitmap;
    private static javax.swing.JCheckBox CBEstatico1Muro;
    private static javax.swing.JCheckBox CBMuro;
    private static javax.swing.JCheckBox CBPuerta;
    private static javax.swing.JList LEstaticos1;
    private static javax.swing.JList LLlaves;
    private static javax.swing.JList LMapas;
    private static javax.swing.JList LTerrenos;
    private javax.swing.JMenu MFile;
    private javax.swing.JMenuBar MMenu;
    private javax.swing.JMenuItem MSalvar;
    private javax.swing.JMenu MVer;
    private javax.swing.JRadioButtonMenuItem RBMMapa;
    private javax.swing.JRadioButtonMenuItem RBMTiposCursor;
    private javax.swing.JRadioButtonMenuItem RBMTiposTerreno;
    private javax.swing.JScrollPane SCeldas1;
    private javax.swing.JScrollPane SLLaves;
    private javax.swing.JScrollPane SMapas;
    private javax.swing.JScrollPane STerrenos;
    private static javax.swing.JTextField TFEstatico1ID;
    private static javax.swing.JTextField TFEstatico1Nombre;
    private static javax.swing.JTextField TFID;
    private static javax.swing.JTextField TFLlaves;
    private static javax.swing.JTextField TFNombre;
    // End of variables declaration//GEN-END:variables
}
