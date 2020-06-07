package model.server_side;

public interface CacheManager<Problem, Solution> {
	public Boolean Check(Problem problem);
	public Solution Extract(Problem problem);
	public void Save(Problem problem,Solution solution);
}