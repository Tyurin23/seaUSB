package ru.tyurin.seausb;


public class Sea {

	//todo error handler
	public static void main(String[] args) throws Exception {
		Model model = new Model();
		Controller controller = new Controller(model);
		View view = new View(controller);
		controller.setView(view);
	}
}
