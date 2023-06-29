package net.wenzuo.mono.test.web;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import org.junit.jupiter.api.Test;

/**
 * @author Catch
 * @since 2023-06-28
 */
class CaseTests {

	private static final PathMatcher PATH_MATCHER = new AntPathMatcher();

	@Test
	void patternTest() {
		String pattern1 = "/api/**/user";
		String pattern2 = "/web/**/user";
		String path = "/api/user";
		String combine = PATH_MATCHER.combine(pattern1, pattern2);
		System.out.println(combine);
		boolean match = PATH_MATCHER.match(pattern1, path);
		System.out.println(match);
	}

}
