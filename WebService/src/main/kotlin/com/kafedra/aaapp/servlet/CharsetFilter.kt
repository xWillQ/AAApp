package com.kafedra.aaapp.servlet

import java.net.URLEncoder
import javax.servlet.*
import javax.servlet.annotation.WebFilter

@WebFilter(urlPatterns = ["echo/*"])
class CharsetFilter : Filter {
    private var encoding: String? = null
    override fun init(config: FilterConfig) {
        encoding = config.getInitParameter("requestEncoding")
        if (encoding == null) encoding = "UTF-8"
    }

    override fun doFilter(request: ServletRequest, response: ServletResponse, next: FilterChain) {
        if (null == request.characterEncoding) {
            request.characterEncoding = encoding
        }

        response.contentType = "text/html; charset=UTF-8"
        response.characterEncoding = "UTF-8"
        next.doFilter(request, response)
    }

    override fun destroy() {}
}