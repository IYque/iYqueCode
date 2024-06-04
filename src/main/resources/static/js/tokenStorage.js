// 存储token
function storeToken(token) {
    localStorage.setItem('iYqueToken', token);
}

// 获取token
function getToken() {
    return localStorage.getItem('iYqueToken');
}

// 删除token
function removeToken() {
    localStorage.removeItem('iYqueToken');
}

(function(open) {
    XMLHttpRequest.prototype.open = function(method, url, async, user, pass) {
        this.addEventListener('readystatechange', function() {
            if (this.readyState === XMLHttpRequest.OPENED) {
                var token = getToken(); // 假设这个函数返回有效的token
                if (token) {
                    this.setRequestHeader('Authorization', 'Bearer ' + token);
                }
            }
        }, false);
        open.apply(this, arguments);
    };
})(XMLHttpRequest.prototype.open);