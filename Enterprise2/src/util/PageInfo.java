package util;

/**
 * @Author ChenHao
 * @Date 2018-08-24 15:07 
 * @Description 
 *
 */

public class PageInfo {
	private int pageCount;
	private int pageNow;
	private int limit;
	private int beginPage;
	private int endPage;
	private int index;
	
	public PageInfo() {
		
	}
	
	public PageInfo(int page, int rows, int numInPage, int numOfPage) {
		// TODO Auto-generated constructor stub
		this.pageCount = rows % numInPage == 0 ? (rows / numInPage): (rows / numInPage) + 1;
		this.limit = numInPage;
		this.pageNow = page;

		this.beginPage = this.pageNow - numOfPage / 2;
		if(this.beginPage <= 1) {
			this.beginPage = 1;
		}
		this.endPage = this.beginPage + numOfPage - 1;
		if(this.endPage >= pageCount) {
			this.endPage = pageCount;
			this.beginPage = pageCount >= numOfPage ? this.endPage - numOfPage + 1 : 1;
		}
		this.index = this.limit * (pageNow - 1);
	}

	public int getPageCount() {
		return pageCount;
	}

	public int getPageNow() {
		return pageNow;
	}


	public int getLimit() {
		return limit;
	}

	public int getBeginPage() {
		return beginPage;
	}

	public int getEndPage() {
		return endPage;
	}


	public int getIndex() {
		return index;
	}
}
