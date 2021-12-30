package demo.batch.step;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

public class CustomWriter<T> implements ItemWriter<T> {
	
	@Override
	public void write(List<? extends T> items) throws Exception {
	}

}
