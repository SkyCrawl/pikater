import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Dimension;
import javax.swing.JComboBox;

public class FilePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel jLabel = null;
	private JComboBox jComboBox = null;
	private JLabel jLabel1 = null;
	private JComboBox jComboBox1 = null;
	private String[] filesList = null;
	/**
	 * This is the default constructor
	 */
	public FilePanel() {
		super();
		initialize();
	}

	public FilePanel(String[] filesList) {
		super();
		this.filesList = filesList;
		initialize();
	}
	
	public String getTestFile() {
		return jComboBox1.getSelectedItem().toString();
	}
	
	public String getTrainFile() {
		return jComboBox.getSelectedItem().toString();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints3.gridy = 0;
		gridBagConstraints3.weightx = 1.0;
		gridBagConstraints3.gridx = 3;
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 2;
		gridBagConstraints2.gridy = 0;
		jLabel1 = new JLabel();
		jLabel1.setText("Test File");
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints1.gridy = 0;
		gridBagConstraints1.weightx = 1.0;
		gridBagConstraints1.gridx = 1;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		jLabel = new JLabel();
		jLabel.setText("Train File");
		this.setSize(311, 24);
		this.setLayout(new GridBagLayout());
		this.add(jLabel, gridBagConstraints);
		this.add(getJComboBox(), gridBagConstraints1);
		this.add(jLabel1, gridBagConstraints2);
		this.add(getJComboBox1(), gridBagConstraints3);
	}

	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox(filesList);
			jComboBox.setPreferredSize(new Dimension(100, 24));
			jComboBox.setEditable(true);
		}
		return jComboBox;
	}

	/**
	 * This method initializes jComboBox1	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox(filesList);
			jComboBox1.setPreferredSize(new Dimension(100, 24));
			jComboBox1.setEditable(true);
		}
		return jComboBox1;
	}

}  //  @jve:decl-index=0:visual-constraint="0,0"
