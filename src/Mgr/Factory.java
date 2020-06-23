package Mgr;

public interface Factory<T extends Manageable> {
	T create();
}
