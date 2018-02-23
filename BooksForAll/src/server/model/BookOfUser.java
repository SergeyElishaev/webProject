/**
 * 
 */
package server.model;

import java.util.ArrayList;

/**
 * @author SeReGa
 *
 */
public class BookOfUser {
	int BookId, UserId, Likes, Reviews, ScrollLocation;
	boolean IsOpen, IsLiked, IsReviewed;
	String Name, Path, ImageUrl, Description;
	private ArrayList<ReviewToShow> ReviewsList;
	
	
	
	/**
	 * @param bookId
	 * @param userId
	 * @param likes
	 * @param reviews
	 * @param isOpen
	 * @param isLiked
	 * @param isReviewed
	 * @param name
	 * @param path
	 * @param imageUrl
	 * @param description
	 * @param scrollLocation
	 * @param reviewsList
	 */
	public BookOfUser(int bookId, int userId, int likes, int reviews, boolean isOpen, boolean isLiked,
			boolean isReviewed, String name, String path, String imageUrl, String description, int scrollLocation,
			ArrayList<ReviewToShow> reviewsList) {
		BookId = bookId;
		UserId = userId;
		Likes = likes;
		Reviews = reviews;
		IsOpen = isOpen;
		IsLiked = isLiked;
		IsReviewed = isReviewed;
		Name = name;
		Path = path;
		ImageUrl = imageUrl;
		Description = description;
		ScrollLocation = scrollLocation;
		setReviewsList(reviewsList);
	}
	/**
	 * @param bookId
	 * @param userId
	 * @param isOpen
	 * @param isLiked
	 * @param isReviewed
	 * @param scrollLocation
	 */
	public BookOfUser(int bookId, int userId, boolean isOpen, boolean isLiked, boolean isReviewed, int scrollLocation) {
		BookId = bookId;
		UserId = userId;
		IsOpen = isOpen;
		IsLiked = isLiked;
		IsReviewed = isReviewed;
		ScrollLocation = scrollLocation;
	}
	/**
	 * @return the bookId
	 */
	public int getBookId() {
		return BookId;
	}
	/**
	 * @param bookId the bookId to set
	 */
	public void setBookId(int bookId) {
		BookId = bookId;
	}
	/**
	 * @return the userId
	 */
	public int getUserId() {
		return UserId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		UserId = userId;
	}
	/**
	 * @return the isOpen
	 */
	public boolean getIsOpen() {
		return IsOpen;
	}
	/**
	 * @param isOpen the isOpen to set
	 */
	public void setIsOpen(boolean isOpen) {
		IsOpen = isOpen;
	}
	/**
	 * @return the isLiked
	 */
	public boolean getIsLiked() {
		return IsLiked;
	}
	/**
	 * @param isLiked the isLiked to set
	 */
	public void setIsLiked(boolean isLiked) {
		IsLiked = isLiked;
	}
	/**
	 * @return the isReviewed
	 */
	public boolean getIsReviewed() {
		return IsReviewed;
	}
	/**
	 * @param isReviewed the isReviewed to set
	 */
	public void setIsReviewed(boolean isReviewed) {
		IsReviewed = isReviewed;
	}
	/**
	 * @return the scrollLocation
	 */
	public int getScrollLocation() {
		return ScrollLocation;
	}
	/**
	 * @param scrollLocation the scrollLocation to set
	 */
	public void setScrollLocation(int scrollLocation) {
		ScrollLocation = scrollLocation;
	}
	/**
	 * @return the reviewsList
	 */
	public ArrayList<ReviewToShow> getReviewsList() {
		return ReviewsList;
	}
	/**
	 * @param reviewsList the reviewsList to set
	 */
	public void setReviewsList(ArrayList<ReviewToShow> reviewsList) {
		ReviewsList = reviewsList;
	}
	
}
