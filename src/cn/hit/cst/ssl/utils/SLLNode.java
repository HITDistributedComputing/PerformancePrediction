package cn.hit.cst.ssl.utils;

public class SLLNode<T extends Sortable>{
	private T element;
	private SLLNode<T> next;
	
	public SLLNode(T element){
		this.element = element;
		this.next = null;
	}
	
	public T getElement() {
		return element;
	}

	public void setElement(T element) {
		this.element = element;
	}

	public boolean hasNext(){
		if (this.next != null) {
			return true;
		}
		else
			return false;
	}
	
	public boolean isMeLarger(SLLNode<T> newNode){
		return this.element.getMyKey() > newNode.getMyKey();
	}
	
	public int getMyKey(){
		return this.element.getMyKey();
	}
	
	public SLLNode<T> getNext(){
		return this.next;
	}
	
	public void setNext(SLLNode<T> next){
		this.next = next;
	}
}
