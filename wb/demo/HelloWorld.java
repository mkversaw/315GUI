package wb.demo;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import java.sql.*;
import java.awt.*;
import java.net.URL;

import javax.swing.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.List;

public class HelloWorld extends Composite {
	private Text searchViewer;
	private Text txtUsernameViewer;
	private Table tableViewer;
	private Text searchAnalyzer;
	public static Connection conn = null; // its important that this is static
	public static Statement stmt = null;

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display, SWT.ON_TOP | SWT.CLOSE);
		shell.setLayout(new GridLayout(1,false));
		
		////////////////////////////////// SQL STUFF
		
		final String username = "csce315_909_1user"; 
		final String password = "somepassword"; // real G's store in plain text
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315_909_1db",username,password);
		} catch (Exception e){
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
		
		JOptionPane.showMessageDialog(null,"Opened database successfully");
		try {
			stmt = conn.createStatement();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,"Error accessing Database." + e);
		}
		/////////////////////////////////
		
		//Image icon = new Image(display,"src/images/logo.jpg"); // top left icon
		Image test;
		
		test = new Image(display, HelloWorld.class.getResourceAsStream("logo.jpg"));
		
		shell.setText("FlixNet"); // displays text on title bar
		shell.setImage(test); // set window icon
		
		HelloWorld world = new HelloWorld(shell, SWT.NONE); // make a new instance of the class, displaying it with the shell we just made
		shell.pack();
		shell.open();
		
		while(!shell.isDisposed()) {
			if(!display.readAndDispatch()) {
				display.sleep();
			}
			//display.dispose();
		}
		
	}

	
	/**
	 * Create the composite.``
	 * @param parent
	 * @param style
	 */
	public HelloWorld(Composite parent, int style) {
		super(parent, SWT.BORDER);
		setToolTipText("");
		setLayout(new GridLayout(12, false));
		
		Label lblFlixnet = new Label(this, SWT.NONE);
		lblFlixnet.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblFlixnet.setText("FlixNet");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		CTabFolder tabFolder = new CTabFolder(this, SWT.BORDER);
		GridData gd_tabFolder = new GridData(SWT.FILL, SWT.FILL, false, false, 12, 1);
		gd_tabFolder.heightHint = 412;
		gd_tabFolder.widthHint = 713;
		tabFolder.setLayoutData(gd_tabFolder);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		CTabItem tbtmAnalyzer = new CTabItem(tabFolder, SWT.NONE);
		tbtmAnalyzer.setText("Analyzer");
		
		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		tbtmAnalyzer.setControl(composite_1);
		composite_1.setLayout(new GridLayout(6, false));
		
		Label lblSearchForMoviesAnalyzer = new Label(composite_1, SWT.CENTER);
		lblSearchForMoviesAnalyzer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblSearchForMoviesAnalyzer.setText("Search For Movies");
		new Label(composite_1, SWT.NONE);
		
		searchAnalyzer = new Text(composite_1, SWT.BORDER);
		GridData gd_searchAnalyzer = new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1);
		gd_searchAnalyzer.widthHint = 268;
		searchAnalyzer.setLayoutData(gd_searchAnalyzer);
		searchAnalyzer.setText("Search...");
		
		Button btnEnterQueryAnalyzer = new Button(composite_1, SWT.NONE);
		btnEnterQueryAnalyzer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				try {
					String textFieldValue = searchAnalyzer.getText(); // get whatever has been written in the search bar
					String sqlStatement = "SELECT titleid, originaltitle, runtimeminutes, genres, averagerating" +
					" FROM titles WHERE originaltitle = "+ "\'" + textFieldValue + "\' LIMIT 1";
				
					ResultSet result = stmt.executeQuery(sqlStatement);
					String outputText = "";
					
					while(result.next()) { // goes through every line the query returns
						outputText += "titleID: " + result.getString("titleid") + "\n" +
						"originaltitle: " + result.getString("originaltitle") + "\n" +
						"runtimeminutes: " + result.getString("runtimeminutes") + "\n" +
						"genres: " + result.getString("genres") + "\n" +
						"averagerating: " + result.getString("averagerating") + "\n";
					}
					JOptionPane.showMessageDialog(null, outputText); // opens a dialog box with the massive output text string
				} catch (Exception error) {
					error.printStackTrace();
					System.err.println(e.getClass().getName()+": "+error.getMessage());
				}
			}
		});
		GridData gd_btnEnterQueryAnalyzer = new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1);
		gd_btnEnterQueryAnalyzer.widthHint = -82;
		btnEnterQueryAnalyzer.setLayoutData(gd_btnEnterQueryAnalyzer);
		btnEnterQueryAnalyzer.setText("Enter Query");
		new Label(composite_1, SWT.NONE);
		
		List listOutputAnalyzer = new List(composite_1, SWT.BORDER | SWT.V_SCROLL);
		
		
		
		
		
		
		
		GridData gd_listOutputAnalyzer = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_listOutputAnalyzer.heightHint = 275;
		gd_listOutputAnalyzer.widthHint = 676;
		listOutputAnalyzer.setLayoutData(gd_listOutputAnalyzer);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		
		Label lblStartDateAnalyzer = new Label(composite_1, SWT.CENTER);
		lblStartDateAnalyzer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblStartDateAnalyzer.setText("Start Date");
		new Label(composite_1, SWT.NONE);
		
		Label lblEndDateAnalyzer = new Label(composite_1, SWT.CENTER);
		GridData gd_lblEndDateAnalyzer = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_lblEndDateAnalyzer.widthHint = 123;
		lblEndDateAnalyzer.setLayoutData(gd_lblEndDateAnalyzer);
		lblEndDateAnalyzer.setText("End Date");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		
		DateTime dateTimeSTARTAnalyzer = new DateTime(composite_1, SWT.BORDER);
		dateTimeSTARTAnalyzer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(composite_1, SWT.NONE);
		
		DateTime dateTimeENDAnalyzer = new DateTime(composite_1, SWT.BORDER);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		
		Button btnGetTopTenAnalyzer = new Button(composite_1, SWT.NONE);
		btnGetTopTenAnalyzer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				try {
					String startDateYear = Integer.toString(dateTimeSTARTAnalyzer.getYear());
					String startDateMonth = Integer.toString(dateTimeSTARTAnalyzer.getMonth());
					String startDateDay = Integer.toString(dateTimeSTARTAnalyzer.getDay());
					
					String endDateYear = Integer.toString(dateTimeENDAnalyzer.getYear());
					String endDateMonth = Integer.toString(dateTimeENDAnalyzer.getMonth());
					String endDateDay = Integer.toString(dateTimeENDAnalyzer.getDay());
					
					
					String startDateCombined = startDateYear + "/" + startDateMonth + "/" + startDateDay;
					String endDateCombined = endDateYear + "/" + endDateMonth + "/" + endDateDay;
						
					String sqlStatement = "select T.originalTitle, C.titleid, count(C.titleid)";
					sqlStatement += " from customerratings C join titles T";
					sqlStatement += " on C.titleid = T.titleid";
					sqlStatement += " where cast(C.date as date) >= " + "\'" + startDateCombined + "\'::date ";
					sqlStatement += "and cast(C.date as date) <= " + "\'" + endDateCombined + "\'::date ";
					sqlStatement += "group by C.titleid, T.originalTitle";
					sqlStatement += " order by count(C.titleid)";
					sqlStatement += " desc limit 10";
						
					ResultSet result = stmt.executeQuery(sqlStatement);
					String outputText = "";
					String potato = "";
					
					while(result.next()) { // goes through every line the query returns
						outputText += "Title: " + result.getString("originaltitle") + "\n";
						potato = "Title: " + result.getString("originaltitle") + "\n";
						listOutputAnalyzer.add(potato);
						// "Number of votes: " + result.getString("count(titleid)") + "\n" + determine how to fix this :)
						// "Average rating: " + result.getString("averagerating") + "\n";
					}
					
					// JOptionPane.showMessageDialog(null, outputText); // opens a dialog box with the output text string
				} catch (Exception error) {
					error.printStackTrace();
					System.err.println(e.getClass().getName()+": "+error.getMessage());
				}
			}
		});
		btnGetTopTenAnalyzer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnGetTopTenAnalyzer.setText("Get Top Ten Movies");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		
		CTabItem tbtmViewer = new CTabItem(tabFolder, SWT.NONE);
		tbtmViewer.setText("Viewer");
		
		Composite composite = new Composite(tabFolder, SWT.NONE);
		tbtmViewer.setControl(composite);
		composite.setLayout(new GridLayout(5, false));
		
		Label lblSearchForMoviesViewer = new Label(composite, SWT.CENTER);
		GridData gd_lblSearchForMoviesViewer = new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1);
		gd_lblSearchForMoviesViewer.widthHint = 171;
		lblSearchForMoviesViewer.setLayoutData(gd_lblSearchForMoviesViewer);
		lblSearchForMoviesViewer.setText("Search For Movies");
		
		searchViewer = new Text(composite, SWT.BORDER);
		GridData gd_searchViewer = new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1);
		gd_searchViewer.widthHint = 216;
		searchViewer.setLayoutData(gd_searchViewer);
		searchViewer.setText("Search...");
		
		Button btnEnterQueryViewer = new Button(composite, SWT.NONE);
		btnEnterQueryViewer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				
			}
		});

		GridData gd_btnEnterQueryViewer = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnEnterQueryViewer.widthHint = 294;
		btnEnterQueryViewer.setLayoutData(gd_btnEnterQueryViewer);
		btnEnterQueryViewer.setText("Enter Query");
		
		Label lblEnterUsernameViewer = new Label(composite, SWT.NONE);
		lblEnterUsernameViewer.setAlignment(SWT.CENTER);
		GridData gd_lblEnterUsernameViewer = new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1);
		gd_lblEnterUsernameViewer.widthHint = 177;
		lblEnterUsernameViewer.setLayoutData(gd_lblEnterUsernameViewer);
		lblEnterUsernameViewer.setText("Enter Username");
		
		txtUsernameViewer = new Text(composite, SWT.BORDER);
		txtUsernameViewer.setText("Username...");
		GridData gd_txtUsernameViewer = new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1);
		gd_txtUsernameViewer.widthHint = 218;
		txtUsernameViewer.setLayoutData(gd_txtUsernameViewer);
		
		Button btnLoginViewer = new Button(composite, SWT.NONE);
		GridData gd_btnLoginViewer = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_btnLoginViewer.widthHint = 210;
		btnLoginViewer.setLayoutData(gd_btnLoginViewer);
		btnLoginViewer.setText("Login");
		
		Label lblStartDateViewer = new Label(composite, SWT.CENTER);
		lblStartDateViewer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblStartDateViewer.setText("Start Date");
		new Label(composite, SWT.NONE);
		
		Label lblEndDateViewer = new Label(composite, SWT.CENTER);
		GridData gd_lblEndDateViewer = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_lblEndDateViewer.widthHint = 121;
		lblEndDateViewer.setLayoutData(gd_lblEndDateViewer);
		lblEndDateViewer.setText("End Date");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		DateTime dateTimeSTARTViewer = new DateTime(composite, SWT.BORDER);
		dateTimeSTARTViewer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(composite, SWT.NONE);
		
		DateTime dateTimeENDViewer = new DateTime(composite, SWT.BORDER);
		GridData gd_dateTimeENDViewer = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_dateTimeENDViewer.widthHint = 129;
		dateTimeENDViewer.setLayoutData(gd_dateTimeENDViewer);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Button btnGetTopTenViewer = new Button(composite, SWT.NONE);
		btnGetTopTenViewer.setText("Get Top Ten Movies");
		new Label(composite, SWT.NONE);
		
		Button btnGetWatchHistoryViewer = new Button(composite, SWT.NONE);
		btnGetWatchHistoryViewer.setText("Get Watch History");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		tableViewer = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gd_tableViewer = new GridData(SWT.FILL, SWT.FILL, true, true, 5, 1);
		gd_tableViewer.widthHint = 151;
		tableViewer.setLayoutData(gd_tableViewer);
		tableViewer.setHeaderVisible(true);
		tableViewer.setLinesVisible(true);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
