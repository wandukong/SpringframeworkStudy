
# Header

## 🔊HttpServletRequest
HttpServletRequest를 통해 request header에 대한 정보를 얻을 수 있다.
- .getMethod() : request method
- .getContextPath() : 	context root
- .getRequestURI() : context root 까지 제외한 경로
- .getRemoteAddr() : client ip 
- .getHeader("헤더이름") : 원하는 header 값을 가져온다.


```java
public String getHeaderValue(HttpServletRequest request) {
		logger.info("method : " + request.getMethod());
		logger.info("requestURI : " + request.getRequestURI());
		logger.info("client IP : " + request.getRemoteAddr());
		logger.info("contextRoot : " + request.getContextPath());

		String userAgent = request.getHeader("User-Agent");
		logger.info("User-Agent : " + userAgent);

		// Client OS
		if (userAgent.contains("Windows NT")) {
			logger.info("client OS : Windows");
		} else if (userAgent.contains("Macintosh")) {
			logger.info("client OS : macOS");
		}

		// Client Browser
		if (userAgent.contains("Edg")) {
			logger.info("client Browser : Edge");
		} else if (userAgent.contains("Trident")) {
			logger.info("client Browser : IE11");
		} else if (userAgent.contains("Chrome")) {
			logger.info("client Browser : Chrome");
		} else if (userAgent.contains("Safari")) {
			logger.info("client Browser : Safari");
		}
		...
	}
```