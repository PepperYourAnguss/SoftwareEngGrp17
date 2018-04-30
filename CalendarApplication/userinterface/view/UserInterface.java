package userinterface.view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import userinterface.model.ActionEvents;
import userinterface.model.Style;
import userinterface.model.Themes;
import userinterface.view.component.Button;
import userinterface.view.component.ProjectTree;


/**
 * @author Tobias
 */
public class UserInterface extends Panel implements ActionEvents, Style {

	private static final long serialVersionUID = 1L;
	private JTextArea	queryRetrievalPanel;
	private JLabel		promptLabel;
	private JScrollPane	queryRetrievalScrollPane;
	private JButton		submitLoginButton;
	private JTextArea	projectPanel;
	private ProjectTree		projecTree;
	private Panel		innerPromptPanel, subPanelempty, subPanelControlView, inputPanel, subPanelUserInput, connectingPanel;

	public UserInterface() {
		super(Themes.DO_NOT_SKIN);
		initializeNestedLayouts();
		initializeMainIOComponents();
		initializeProjectTextArea();

		userPrompt();
		setAndAttachLayouts();
		setInputPanelLayout();
	}

	private void setAndAttachLayouts() {
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "SoftwareHuset A/S", 0, 0,
				(Style.Fonts.TITLED_BORDER.getFont()), Color.DARK_GRAY));
		
		this.setLayout(new BorderLayout());
		connectingPanel = new Panel(new BorderLayout(),Themes.DO_NOT_SKIN);
		innerPromptPanel.add(queryRetrievalScrollPane, BorderLayout.CENTER);
		innerPromptPanel.add(promptLabel, BorderLayout.SOUTH);
		connectingPanel.add(projecTree, BorderLayout.WEST);
		connectingPanel.add(innerPromptPanel, BorderLayout.CENTER);
		connectingPanel.add(subPanelempty, BorderLayout.SOUTH);
		this.add(connectingPanel,BorderLayout.CENTER);
	}

	private void setInputPanelLayout() {

		inputPanel = new Panel(Themes.DO_NOT_SKIN);
		inputPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		inputPanel.add(new Button("Logout", new Logout(), "UI"));

		subPanelControlView = new Panel(Themes.DO_NOT_SKIN);
		subPanelControlView.setLayout(new FlowLayout(FlowLayout.RIGHT));

		subPanelUserInput.add(inputPanel);
		subPanelempty.add(subPanelControlView, 0);
		subPanelempty.add(subPanelUserInput, 1);
	}

	private void initializeProjectTextArea() {
		projectPanel = new JTextArea(5, 28);
		projectPanel.setAutoscrolls(false);
		projectPanel.setLineWrap(false);
		projectPanel.setFont(new Font("Arial", Font.PLAIN, 12));
		projectPanel.setEditable(false);
		projecTree = new ProjectTree();
	}

	private void userPrompt() {
		promptLabel = new JLabel(" ");
		promptLabel.setBorder(BorderFactory.createEtchedBorder());
	}

	private void initializeMainIOComponents() {
		// rows and columns locks the textarea from auto-rezizing on input.
		queryRetrievalPanel = new JTextArea(5, 10);
		queryRetrievalPanel.setAutoscrolls(true);
		queryRetrievalPanel.setSize(400, 200);// TODO: set x to the value of a getter from init in GUI.
		queryRetrievalPanel.setLineWrap(true);
		queryRetrievalPanel.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		queryRetrievalPanel.setEditable(false);
		queryRetrievalScrollPane = new JScrollPane(queryRetrievalPanel);
		queryRetrievalScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	}

	private void initializeNestedLayouts() {
		innerPromptPanel = new Panel(Themes.DO_NOT_SKIN);
		subPanelempty = new Panel(Themes.DO_NOT_SKIN);
		innerPromptPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		subPanelempty = new Panel(Themes.DO_NOT_SKIN);
		subPanelempty.setBorder(BorderFactory.createEtchedBorder());
		innerPromptPanel.setLayout(new BorderLayout());
		subPanelempty.setLayout(new GridLayout(3, 1));
		subPanelUserInput = new Panel(Themes.DO_NOT_SKIN);
	}

	public void setProjectOrActivityMessage(String setTextPane) {
		queryRetrievalPanel.setText(setTextPane);
	}

	public String getPromptLabel() {
		return promptLabel.getText();
	}

	public void setPromptLabel(String string) {
		promptLabel.setText(string);
	}

	public JButton getLoginButton() {
		return submitLoginButton;
	}

}
