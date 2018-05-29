package apricot.dom;

public enum DuplicationType {
	/** 重複なし
	 * <--->
	 *       <--->
	 * or
	 *       <--->
	 * <--->
	 */
	noDuplication,
	
	/** 覆っている
	 * <--------->
	 *    <----> 
	 */
	cover,

	/** 含まれている
	 *   <---->
	 * <--------->    
	 */
	beCovered,
	
	/** 追い越されている
	 * <------>
	 *     <------>    
	 */
	beOvertaken,

	/** 追い越している
	 *     <------>    
	 * <------>
	 */
	overtake,
	
	/** 同時に始まる
	 * <------------>    
	 * <------>
	 */
	startSame,
	/** 同時に終わる
	 * <------------>    
	 *       <------>
	 */
	endSame,
}
