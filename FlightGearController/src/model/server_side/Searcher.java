package model.server_side;

public interface Searcher<Solution> {
	public Solution search(Searchable searchable);
	public int getNumberOfNodesEvaluated();
}