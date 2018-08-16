import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.List;


public class KeyEventDemo extends JFrame
		implements KeyListener,
		ActionListener
{
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	private static final String FILE_HEADER = "User,H.period,DD.period.t,UD.period.t,H.t,DD.t.i,UD.t.i,H.i,DD.i.e,UD.i.e," +
			"H.e,DD.e.five,UD.e.five,H.five,DD.five.Shift.r,UD.five.Shift.r,H.Shift.r,DD.Shift.r.o,UD.Shift.r.o,H.o," +
			"DD.o.a,UD.o.a,H.a,DD.a.n,UD.a.n,H.n,DD.n.l,UD.n.l,H.l,DD.l.Return,UD.l.Return,H.Return";
	private static boolean flag = true;
	JTextArea displayArea;
	JTextField typingArea;

	static final String newline = System.getProperty("line.separator");

	public static void main(String[] args) {

        /* Use an appropriate Look and Feel */
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
        /* Turn off metal's use of bold fonts */
		UIManager.put("swing.boldMetal", Boolean.FALSE);

		//Schedule a job for event dispatch thread:
		//creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	/**
	 * Create the GUI and show it.  For thread safety,
	 * this method should be invoked from the
	 * event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		//Create and set up the window.
		KeyEventDemo frame = new KeyEventDemo("Keystroke Logging Application");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Set up the content pane.
		frame.addComponentsToPane();


		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	private void addComponentsToPane()  {

		JButton button = new JButton("Clear");
		button.addActionListener(this);



		typingArea = new JTextField(20);
		typingArea.addKeyListener(this);
		displayArea = new JTextArea();
		displayArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(displayArea);
		scrollPane.setPreferredSize(new Dimension(500, 250));

		getContentPane().add(typingArea, BorderLayout.PAGE_START);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		getContentPane().add(button, BorderLayout.PAGE_END);
	}

	KeyDataStore store;
    int pressIndex = 0;
    int releaseIndex = 0;

	public KeyEventDemo(String name) {
	    super(name);
        this.store = new KeyDataStore();
	}

	/** Handle the key typed event from the text field. */
	public void keyTyped(KeyEvent e) {


	}

	/** Handle the key pressed event from the text field. */
	public void keyPressed(KeyEvent e) {
		displayArea.setText("");

		if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
			typingArea.setText("");
			this.store.removeContainsOfMap();
			releaseIndex=0;
			pressIndex = 0;
		}


		if(e.getKeyCode() == KeyEvent.VK_ENTER){

			String str = typingArea.getText();
			System.out.println(str);
			//if( str.equals(".tie5Roanl")){
				List<Double> keyStoreParams = this.store.process();
				List<Long> keyStoreParamsInNano = this.store.processInNano();
				System.out.println(keyStoreParamsInNano.toString());
				System.out.println(keyStoreParams.toString());
				System.out.println(keyStoreParams.size());
				this.store.initialize();
				pressIndex = 0;
				typingArea.setText("");
				generateCsv(keyStoreParams,"");
				generateCsvInNano(keyStoreParamsInNano,"");
				return;
//
//			//}else{
//				displayArea.setText(str);
//				displayArea.setText("Please insert correct password");
//				typingArea.setText("");
//				this.store.removeContainsOfMap();
//				releaseIndex=0;
//				pressIndex = 0;
//				return;
//
//			}


        }

		TypedKeyObject typedKeyObject = new TypedKeyObject();
		typedKeyObject.pressTime = System.currentTimeMillis();
		typedKeyObject.pressTimeinNano = System.nanoTime();
		this.store.storeTypedObject(pressIndex,typedKeyObject);
		pressIndex++;
	}

	/** Handle the key released event from the text field. */
	public void keyReleased(KeyEvent e) {
		//displayInfo(e, "KEY RELEASED: ");
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
		    releaseIndex = 0;
		    return;
        }
		if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
			typingArea.setText("");
			this.store.removeContainsOfMap();
			pressIndex = 0;
			releaseIndex=0;
			return;
		}
		if(this.store.getKey(releaseIndex)!=null) {
          this.store.getKey(releaseIndex).releaseTime = System.currentTimeMillis();
          this.store.getKey(releaseIndex).releaseTimeinNano = System.nanoTime();

			releaseIndex++;
        }
	}

	/**
	 * Handle the button click.
	 */
	public void actionPerformed(ActionEvent e) {
		//Clear the text components.
		displayArea.setText("");
		typingArea.setText("");
		//Return the focus to the typing area.
		typingArea.requestFocusInWindow();
	}


	/**
	 *  This function create csv which stores your keystroke eachtime after
	 *  you enter valid password.
	 *  Function is used data collection. This data will be used for
	 *  training and tesing machine learning model.
	 * @param keyStoreParams : list of keyhold,kyup,keydown timings
	 */
	public void generateCsv(List<Double> keyStoreParams,String userName) {

		FileWriter fileWriter = null;
		try {

			String filename = userName+"Keystrokes.csv";
			File file = new File(filename);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			fileWriter = new FileWriter(file.getAbsoluteFile(), true);


			BufferedReader reader = new BufferedReader(new FileReader(filename));

			if (reader.readLine() == null) {
				fileWriter.append(FILE_HEADER.toString());
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
			fileWriter.append(userName);
			fileWriter.append(COMMA_DELIMITER);
			for (int j = 0; j < keyStoreParams.size() - 1; j++) {
				fileWriter.append("" + keyStoreParams.get(j));
				fileWriter.append(COMMA_DELIMITER);
			}
			fileWriter.append("" + keyStoreParams.get(keyStoreParams.size() - 1));

			fileWriter.append(NEW_LINE_SEPARATOR);
			System.out.println("CSV file was created successfully !!!");


			typingArea.setText("");
			//Return the focus to the typing area.
			displayArea.setText("Typing pattern is: \n" + keyStoreParams.toString());
			typingArea.requestFocusInWindow();

		} catch (Exception e2) {
			System.out.println("Error in CsvFileWriter !!!");
			e2.printStackTrace();
		} finally {

			try {
				fileWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public void generateCsvInNano(List<Long> keyStoreParams,String userName) {

		FileWriter fileWriter = null;
		try {
			String fileName = userName+"KeystrokesInNano.csv";
			//fileWriter = null;
			File file = new File(fileName);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			fileWriter = new FileWriter(file.getAbsoluteFile(), true);


			BufferedReader reader = new BufferedReader(new FileReader(fileName));

			if (reader.readLine() == null) {
				fileWriter.append(FILE_HEADER.toString());
				fileWriter.append(NEW_LINE_SEPARATOR);
			}



			fileWriter.append(userName);
			fileWriter.append(COMMA_DELIMITER);
			for (int j = 0; j < keyStoreParams.size() - 1; j++) {
				fileWriter.append("" + keyStoreParams.get(j));
				fileWriter.append(COMMA_DELIMITER);
			}
			fileWriter.append("" + keyStoreParams.get(keyStoreParams.size() - 1));

			fileWriter.append(NEW_LINE_SEPARATOR);
			System.out.println("CSV file was created successfully !!!");

			typingArea.setText("");
			//Return the focus to the typing area.
		//	displayArea.setText(keyStoreParams.toString());
			typingArea.requestFocusInWindow();

		} catch (Exception e2) {
			System.out.println("Error in CsvFileWriter !!!");
			e2.printStackTrace();
		} finally {

			try {
				fileWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}

