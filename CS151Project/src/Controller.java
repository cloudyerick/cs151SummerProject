import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
	
	private Model model;
	private View view;
	
	public Controller(Model theModel, View theView){
		this.view = theView;
		this.model = theModel;
		
		//
		view.addEventActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				createEvent();
			}
		});
		
	
	}
	
	public void createEvent(){
		//create event stuff
	}
}
