module RealityTips {
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.controls;
	
	opens application to javafx.graphics, javafx.fxml;
}