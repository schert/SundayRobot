package core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Action implements Serializable {

	private static final long serialVersionUID = 1L;
	private AbstractAction classAction;
	private long timeAfter = 0;
	private Long frame = null;
	private long timeBefore = 0;
	private Map<String,Object> params = new HashMap<>();
	
	public Action(AbstractAction classAction) {
		this.classAction = classAction;
	}

	public Map<String, Object> getParamsMap() {
		return params;
	}

	public AbstractAction getClassAction() {
		return classAction;
	}

	public void setClassAction(AbstractAction classAction) {
		this.classAction = classAction;
	}

	public long getTimeAfter() {
		return timeAfter;
	}

	public void setTimeAfter(long timeAfter) {
		this.timeAfter = timeAfter;
	}
	
	@Override
	public String toString() {
		return classAction.getActionName() + " : " + params.toString();
	}

	public long getTimeBefore() {
		return timeBefore;
	}

	public void setTimeBefore(long timeBefore) {
		this.timeBefore = timeBefore;
	}

	public Long getFrame() {
		return frame;
	}

	public void setFrame(Long frame) {
		this.frame = frame;
	}
}
