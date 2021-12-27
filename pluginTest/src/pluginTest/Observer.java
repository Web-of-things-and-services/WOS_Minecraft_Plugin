package pluginTest;

import team.Team;

public interface Observer {
	public void update(Team team);
	public void reset();
}
