package busyShedule.model.bizmodel;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ArrayListGame {
	public List<Game> list;

	public ArrayListGame(List<Game> l) {
		this.list = l;
	}

	public ArrayListGame() {
	}
}
