package rocket.app.view;

import eNums.eAction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import rocket.app.MainApp;
import rocketCode.Action;
import rocketData.LoanRequest;
import exceptions.RateException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import rocket.app.MainApp;
import rocketBase.RateBLL;
import rocketCode.Action;
import rocketData.LoanRequest;
public class MortgageController {

	private MainApp mainApp;
	
	//	TODO - RocketClient.RocketMainController
	
	//	Create private instance variables for:
	//		TextBox  - 	txtIncome
	//		TextBox  - 	txtExpenses
	//		TextBox  - 	txtCreditScore
	//		TextBox  - 	txtHouseCost
	//		ComboBox -	loan term... 15 year or 30 year
	//		Labels   -  various labels for the controls
	//		Button   -  button to calculate the loan payment
	//		Label    -  to show error messages (exception throw, payment exception)

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	private TextField txtIncome;

	@FXML
	private TextField txtExpenses;

	@FXML
	private TextField txtCreditScore;

	@FXML
	private TextField txtRate;

	@FXML
	private TextField txtHouseCost;

	@FXML
	private TextField txtDownPayment;

	@FXML
	private ComboBox<String> cmbTerm;

	@FXML
	private TextField txtMonthlyPayment;

	@FXML
	private Button btnPayment;

	@FXML
	private Label lblCreditScore;

	@FXML
	private Label lblHouseCost;

	@FXML
	private Label lblDownPayment;

	@FXML
	private Label lblRate;

	@FXML
	private Label lblIncome;

	@FXML
	private Label lblExpenses;

	@FXML
	private Label lblTerm;

	@FXML
	private Label lblError;

	@FXML
	private Label lblMortgagePayment;
	
	@FXML
	private void initialize() {
		cmbTerm.getItems().add("30");
		cmbTerm.getSelectionModel().select("30");
	}
	
	//	TODO - RocketClient.RocketMainController
	//			Call this when btnPayment is pressed, calculate the payment
	@FXML
	public void btnCalculatePayment(ActionEvent event)
	{
		Object message = null;
		//	TODO - RocketClient.RocketMainController
		
		Action a = new Action(eAction.CalculatePayment);
		LoanRequest lq = new LoanRequest();
		//	TODO - RocketClient.RocketMainController
		//			set the loan request details...  rate, term, amount, credit score, downpayment
		//			I've created you an instance of lq...  execute the setters in lq
		lq.setdAmount(Double.parseDouble(txtHouseCost.getText()));
		lq.setiDownPayment(Integer.parseInt(txtDownPayment.getText()));
		lq.setExpenses(Double.parseDouble(txtExpenses.getText()));
		lq.setiCreditScore(Integer.parseInt(txtCreditScore.getText()));
		a.setLoanRequest(lq);
		
		try {
			lq.setdRate(RateBLL.getRate(lq.getiCreditScore()));
		} catch (RateException e) {
			lq.setdRate(-1);
			
		}
		
		
		lq.setiTerm(Integer.parseInt(cmbTerm.getSelectionModel().getSelectedItem().toString()));
		
		//	send lq as a message to RocketHub		
		mainApp.messageSend(lq);
	}
	
	public void HandleLoanRequestDetails(LoanRequest lRequest)
	{
		//	TODO - RocketClient.HandleLoanRequestDetails
		//			lRequest is an instance of LoanRequest.
		//			after it's returned back from the server, the payment (dPayment)
		//			should be calculated.
		//			Display dPayment on the form, rounded to two decimal places
		double pmt = lRequest.getdPayment();
		double rate = lRequest.getdRate();
		
		if (rate == -1) {
			rate = lRequest.getIncome()*.28;
			
		}
		
		else{
			rate = (lRequest.getIncome()*.36)- lRequest.getExpenses();
			txtMonthlyPayment.setText("House Cost too high");
		}
		
		
		
	}
}
