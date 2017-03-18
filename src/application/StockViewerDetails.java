package application;

import java.io.BufferedReader;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class StockViewerDetails extends Stage {
	
	private List<StockDataDetails> values;
	private int number;
	private StockData data;

	public StockViewerDetails(StockData data, int number){
		this.data = data;
		this.number = number;
		this.setTitle(data.getCompanyName());
		setResizable(true);
		Scene scene = new Scene(createGUI(), 535, 600);
		setScene(scene);
	}

	public Parent createGUI() {
		AnchorPane pane = new AnchorPane();
		
		TableView<StockDataDetails> tab = new TableView<>(getItems());
		tab.setLayoutX(20);
		tab.setLayoutY(20);
		
		TableColumn dateCol = new TableColumn("Date");
		dateCol.setCellValueFactory(new PropertyValueFactory("date"));
		TableColumn openCol = new TableColumn("Open");
		openCol.setCellValueFactory(new PropertyValueFactory("open"));
		TableColumn highCol = new TableColumn("High");
		highCol.setCellValueFactory(new PropertyValueFactory("high"));
		TableColumn lowCol = new TableColumn("Low");
		lowCol.setCellValueFactory(new PropertyValueFactory("low"));
		TableColumn closeCol = new TableColumn("Close");
		closeCol.setCellValueFactory(new PropertyValueFactory("close"));
		TableColumn volumeCol = new TableColumn("Volume");
		volumeCol.setCellValueFactory(new PropertyValueFactory("volume"));
		TableColumn adjCloseCol = new TableColumn("Adj Close");
		adjCloseCol.setCellValueFactory(new PropertyValueFactory("adjClose"));
		
		tab.getColumns().setAll(dateCol, openCol, highCol, lowCol, closeCol, volumeCol, adjCloseCol);
		
		tab.setPrefWidth(500);
		tab.setPrefHeight(500);
		tab.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		Button btn = new Button("Generate Report");
		btn.setOnAction(e ->{
			generateReport();
		});
		
		Button btn2 = new Button("Generate Graph");
		btn2.setOnAction(e ->{
			generateGraph();
		});
			
		btn.setLayoutX(25);
		btn.setLayoutY(540);
		
		btn2.setLayoutX(400);
		btn2.setLayoutY(540);
		
		pane.getChildren().addAll(tab, btn, btn2);
		return pane;
	}

	private void generateGraph(){
		 Stage stage = new Stage();
		        stage.setTitle("Stock for " + data.getCompanyName());//+ companyName);
		        
		        
		        //defining the axes
		        final NumberAxis highXAxis = new NumberAxis();
		        final NumberAxis highYAxis = new NumberAxis();
		        
		        
		        final NumberAxis lowXAxis = new NumberAxis();
		        final NumberAxis lowYAxis = new NumberAxis();
		        
		        final NumberAxis volumeXAxis = new NumberAxis();
		        final NumberAxis volumeYAxis = new NumberAxis();
		        
		        final NumberAxis openXAxis = new NumberAxis();
		        final NumberAxis openYAxis = new NumberAxis();
		        
		        final NumberAxis closeXAxis = new NumberAxis();
		        final NumberAxis closeYAxis = new NumberAxis();
		        
		        
		        //define labels
		        highXAxis.setLabel("Day Number");
		        highYAxis.setLabel("Price");
		        highYAxis.setAutoRanging(true);
		        
		        lowXAxis.setLabel("Day Number");
		        lowYAxis.setLabel("Price");
		        lowYAxis.setAutoRanging(true);
		        
		        volumeXAxis.setLabel("Day Number");
		        volumeYAxis.setLabel("Price");
		        volumeYAxis.setAutoRanging(true);
		        
		        openXAxis.setLabel("Day Number");
		        openYAxis.setLabel("Price");
		        openYAxis.setAutoRanging(true);
		        
		        closeXAxis.setLabel("Day Number");
		        closeYAxis.setLabel("Price");
		        closeYAxis.setAutoRanging(true);
		        //creating the chart

		        final LineChart<Number,Number> highLineChart = 
		                new LineChart<Number,Number>(highXAxis,highYAxis);
		        final LineChart<Number,Number> lowLineChart = 
		                new LineChart<Number,Number>(lowXAxis,lowYAxis);
		        final LineChart<Number,Number> volumeLineChart = 
		                new LineChart<Number,Number>(volumeXAxis,volumeYAxis);
		        final LineChart<Number,Number> openLineChart = 
		                new LineChart<Number,Number>(openXAxis,openYAxis);
		        final LineChart<Number,Number> closeLineChart = 
		                new LineChart<Number,Number>(closeXAxis,closeYAxis);
		        
		        
		        //Sets Line chart in range
		        highYAxis.setForceZeroInRange(false);
		        highLineChart.setAnimated(false);
		        
		        lowYAxis.setForceZeroInRange(false);
		        lowLineChart.setAnimated(false);
		        
		        volumeYAxis.setForceZeroInRange(false);
		        volumeLineChart.setAnimated(false);
		        
		        openYAxis.setForceZeroInRange(false);
		        openLineChart.setAnimated(false);
		        
		        closeYAxis.setForceZeroInRange(false);
		        closeLineChart.setAnimated(false);
       
		        //Chart Titles
		        highLineChart.setTitle("High: "  + data.getCompanyName());
		        highLineChart.setCreateSymbols(false);
		        
		        lowLineChart.setTitle("Low: "  + data.getCompanyName());
		        lowLineChart.setCreateSymbols(false);
		        
		        volumeLineChart.setTitle("Volume: "  + data.getCompanyName());
		        volumeLineChart.setCreateSymbols(false);
		        
		        openLineChart.setTitle("Open: "  + data.getCompanyName());
		        openLineChart.setCreateSymbols(false);
		        
		        closeLineChart.setTitle("Close: "  + data.getCompanyName());
		        closeLineChart.setCreateSymbols(false);
		        
		        //defining a series
		        XYChart.Series highSeries = new XYChart.Series();
		        XYChart.Series lowSeries = new XYChart.Series();
		        XYChart.Series volumeSeries = new XYChart.Series();
		        XYChart.Series openSeries = new XYChart.Series();
		        XYChart.Series closeSeries = new XYChart.Series();
		        highSeries.setName("High");
		        lowSeries.setName("Low");
		        volumeSeries.setName("Volume");
		        openSeries.setName("Open");
		        closeSeries.setName("Close");
		        
		       
		        highLineChart.getData().add(highSeries);
		        lowLineChart.getData().add(lowSeries);
		        volumeLineChart.getData().add(volumeSeries);
		        openLineChart.getData().add(openSeries);
		        closeLineChart.getData().add(closeSeries);
		        
		        StockDataDetails current = values.get(0);
				int i = 0;
				for (StockDataDetails details : values) {
					//details = current;
					i++;
				
					highSeries.getData().add(new XYChart.Data(i,details.getHigh()));
					  
					lowSeries.getData().add(new XYChart.Data(i,details.getLow()));
					
					volumeSeries.getData().add(new XYChart.Data(i,details.getVolume()));
					
					openSeries.getData().add(new XYChart.Data(i,details.getOpen()));
					
					closeSeries.getData().add(new XYChart.Data(i,details.getClose()));
	
				}	
		        
		        TabPane tabPane = new TabPane();
		        Tab highTab = new Tab();
		        highTab.setText("High");
		        highTab.setContent(highLineChart);
		        Tab lowTab = new Tab();
		        lowTab.setText("Low");
		        lowTab.setContent(lowLineChart);
		        Tab volumeTab = new Tab();
		        volumeTab.setText("Volume");
		        volumeTab.setContent(volumeLineChart);
		        Tab openTab = new Tab();
		        openTab.setText("Open");
		        openTab.setContent(openLineChart);
		        Tab closeTab = new Tab();
		        closeTab.setText("Close");
		        closeTab.setContent(closeLineChart);
		        
		        tabPane.getTabs().addAll(highTab,lowTab,volumeTab,openTab,closeTab);		        
			      
		     	
		       
		        Scene scene  = new Scene(tabPane,800,600);
		        stage.setScene(scene);
		        stage.show();

	}
	
	
	private void generateReport() {		
		StockDataDetails highest = values.get(0);
		StockDataDetails lowest = values.get(0);
		Double average = 0.0;
		for (StockDataDetails details : values) {
			if(details.getHigh().compareTo(highest.getHigh()) > 0){
				highest = details;
			}
			if(details.getLow().compareTo(lowest.getLow()) < 0){
				lowest = details;
			}
			average += details.getClose();
		}
		
		DecimalFormat df = new DecimalFormat("#.00"); 
		Double closeAverage = average / values.size();		
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save file");
		fileChooser.setInitialFileName(data.getCompanyName()+"_Report.txt");
		File savedFile = fileChooser.showSaveDialog(this);

		if (savedFile != null) {

			try {
				FileWriter writer = new FileWriter(savedFile, false);
				BufferedWriter bufferedWriter = new BufferedWriter(writer);
				
				bufferedWriter.write("Number: " + number +" \n");
				bufferedWriter.write("Stock Symbol: " + data.getStockSymbol() +" \n");
				bufferedWriter.write("Company Name: " + data.getCompanyName() +" \n");
				bufferedWriter.write("Highest: " + highest.getDate() +" - "+highest.getHigh() +" \n");
				bufferedWriter.write("Lowest: " + lowest.getDate()+" - "+lowest.getLow() +" \n");
				bufferedWriter.write("Average close: " + df.format(closeAverage) +" \n");
				bufferedWriter.write("Close: " + data.getLatestSharePrice() +" \n");
				
				bufferedWriter.close();
				writer.close();
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	private ObservableList<StockDataDetails> getItems() {
		values = new ArrayList<>();
		try {
			File f1 = new File("src/application/"+ data.getCsvFile());
			FileReader reader = new FileReader(f1);
			BufferedReader bufferedReader = new BufferedReader(reader);
			
			String line;
			int first = 0;
            while ((line = bufferedReader.readLine()) != null) {
            	if(line == null || line.isEmpty()){
            		continue;
            	}
            	if(first == 0){ //skips the first line
            		first++;
            		continue;
            	}
            	String dt = line.split(",")[0].trim();
            	String op = line.split(",")[1].trim();
            	String hg = line.split(",")[2].trim();
            	String lw = line.split(",")[3].trim();
            	String cl = line.split(",")[4].trim();
            	String vl = line.split(",")[5].trim();
            	String ac = line.split(",")[6].trim();
            	
            	values.add(new StockDataDetails(dt, doubleVal(op), doubleVal(hg), doubleVal(lw), doubleVal(cl), doubleVal(vl), doubleVal(ac)));
            }            
            reader.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return FXCollections.observableArrayList(values);
	}

	private Double doubleVal(String val) {
		return Double.parseDouble(val);
	}

}
