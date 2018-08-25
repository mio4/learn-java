//用于保存文件信息
public class FileInfo {
	private String path; //文件绝对路径
	private String name; //文件名称
	private long size; //文件大小
	private long time; //文件最后修改时间
	
	public FileInfo(String path,String name, long size, long time) {
		this.path = path;
		this.name = name;
		this.size = size;
		this.time = time;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
	public String getPath() {
		return this.path;
	}
	public String getName() {
		return this.name;
	}
	public long getSize() {
		return this.size;
	}
	public long getTime() {
		return this.time;
	}
}
