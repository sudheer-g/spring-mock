package servletOps;

import java.util.Objects;

public class PatternMatcher {
    private String queryString;

    public PatternMatcher(String queryString) {
        this.queryString = queryString;
    }


    private boolean matchPathVariables(String pattern, String path) {
        return pattern.startsWith("{") && pattern.endsWith("}") && path.matches("^[0-9]+$");
    }

    private boolean isQueryStringPresent(String pattern) {
        return pattern.contains("?");
    }

    private String[] getPatternPath(String pattern) {
        String[] patternPath = null;
        if (isQueryStringPresent(pattern)) {
            patternPath = pattern.substring(1, pattern.indexOf("?")).split("/");
        } else {
            patternPath = pattern.substring(1).split("/");
        }
        return patternPath;
    }

    private int skipPathParam(char[] charArray, int index) {
        while (index < charArray.length) {
            if (charArray[index] == '&') {
                break;
            }
            index++;
        }
        return index;
    }

    private String getPatternQueryString(String pattern) {
        String patternQueryString = null;
        if (isQueryStringPresent(pattern)) {
            patternQueryString = pattern.substring(pattern.indexOf("?") + 1);
        }
        return patternQueryString;
    }

    private boolean queryStringMatcher(String patternQueryString) {
        boolean matched = true;
        char[] queryCharArray = queryString.toCharArray();
        char[] patternCharArray = patternQueryString.toCharArray();
        int queryIndex = 0, patternIndex = 0;
        while (queryIndex < queryCharArray.length && patternIndex < patternCharArray.length) {
            if (patternCharArray[patternIndex] == queryCharArray[queryIndex]) {
                patternIndex++;
                queryIndex++;
            } else if (patternCharArray[patternIndex] == '{') {
                queryIndex = skipPathParam(queryCharArray, queryIndex);
                patternIndex = skipPathParam(patternCharArray, patternIndex);
            } else {
                matched = false;
                break;
            }
        }
        return matched;
    }

    private boolean isQueryStringMatch(String patternQueryString) {
        boolean matched = false;
        if (queryString == null && patternQueryString == null) {
            matched = true;
        }
        if (queryString != null && patternQueryString != null) {
            matched = queryStringMatcher(patternQueryString);
        }
        return matched;
    }

    private boolean pathPatternMatcher(String[] patternList, String[] pathList) {
        int patternListLength = patternList.length;
        int pathListLength = pathList.length;
        boolean match = true;
        int pathListIndex = 0;
        if (patternListLength == pathListLength) {
            for (String patternCell : patternList) {
                if (!matchPathVariables(patternCell, pathList[pathListIndex])
                        && !Objects.equals(patternCell, pathList[pathListIndex])) {
                    match = false;
                    break;
                }
                pathListIndex++;
            }
        } else {
            match = false;
        }
        return match;
    }

    public boolean isPathPatternMatch(String pattern, String path) {
        String[] patternList = getPatternPath(pattern);
        String[] pathList = path.substring(1).split("/");
        String patternQueryString = getPatternQueryString(pattern);
        return pathPatternMatcher(patternList, pathList) && isQueryStringMatch(patternQueryString);
    }

}
