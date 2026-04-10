/**
 * 错误处理工具
 * 用于统一处理系统中的错误，为用户提供明确的错误提示
 */

// 错误类型定义
const ERROR_TYPES = {
  NETWORK: 'network',
  AUTH: 'auth',
  VALIDATION: 'validation',
  SERVER: 'server',
  UNKNOWN: 'unknown'
};

// 错误消息映射
const ERROR_MESSAGES = {
  [ERROR_TYPES.NETWORK]: '网络连接失败，请检查网络设置后重试',
  [ERROR_TYPES.AUTH]: '登录已过期，请重新登录',
  [ERROR_TYPES.VALIDATION]: '输入信息有误，请检查后重试',
  [ERROR_TYPES.SERVER]: '服务器错误，请稍后重试',
  [ERROR_TYPES.UNKNOWN]: '未知错误，请稍后重试'
};

/**
 * 错误处理类
 */
class ErrorHandler {
  /**
   * 处理错误
   * @param {Error} error - 错误对象
   * @param {string} customMessage - 自定义错误消息
   */
  static handleError(error, customMessage = '') {
    console.error('Error occurred:', error);
    // vue-resource 错误在 body 中，axios 在 response.data 中
    const errorData = error.body || (error.response && error.response.data);
    let errorMessage = customMessage || ERROR_MESSAGES[ERROR_TYPES.UNKNOWN];

    if (errorData && errorData.msg) {
      errorMessage = errorData.msg;
    } else if (error.message && error.message.includes('Network Error')) {
      errorMessage = customMessage || ERROR_MESSAGES[ERROR_TYPES.NETWORK];
    } else if (error.status === 401) {
      errorMessage = customMessage || ERROR_MESSAGES[ERROR_TYPES.AUTH];
      setTimeout(() => { window.location.href = '/login' }, 1500);
    } else if (error.status === 400) {
      errorMessage = customMessage || ERROR_MESSAGES[ERROR_TYPES.VALIDATION];
    } else if (error.status >= 500) {
      errorMessage = customMessage || ERROR_MESSAGES[ERROR_TYPES.SERVER];
    } else if (error.statusText) {
      errorMessage = error.statusText;
    }

    this.showError(errorMessage);
    return { type: ERROR_TYPES.UNKNOWN, message: errorMessage };
  }

  /**
   * 显示错误提示
   * @param {string} message - 错误消息
   */
  static showError(message) {
    const $message = this._getMessage()
    if ($message) {
      $message.error(message)
    } else {
      console.error('Error:', message)
      alert(message)
    }
  }

  /**
   * 显示成功提示
   * @param {string} message - 成功消息
   */
  static showSuccess(message) {
    const $message = this._getMessage()
    if ($message) {
      $message.success(message)
    } else {
      alert(message)
    }
  }

  /**
   * 显示警告提示
   * @param {string} message - 警告消息
   */
  static showWarning(message) {
    const $message = this._getMessage()
    if ($message) {
      $message.warning(message)
    } else {
      alert(message)
    }
  }

  /**
   * 显示信息提示
   * @param {string} message - 信息消息
   */
  static showInfo(message) {
    const $message = this._getMessage()
    if ($message) {
      $message.info(message)
    } else {
      alert(message)
    }
  }

  /**
   * 获取 Vue 实例的 $message
   * @returns {Function|null}
   */
  static _getMessage() {
    if (window.__vueApp__ && window.__vueApp__.$message) {
      return window.__vueApp__.$message
    }
    if (Vue && Vue.prototype && Vue.prototype.$message) {
      return Vue.prototype.$message
    }
    return null
  }

  /**
   * 包装异步函数，添加错误处理
   * @param {Function} asyncFn - 异步函数
   * @param {string} customMessage - 自定义错误消息
   * @returns {Function} 包装后的函数
   */
  static wrapAsync(asyncFn, customMessage = '') {
    return async (...args) => {
      try {
        return await asyncFn(...args);
      } catch (error) {
        // 不再调用 handleError，避免显示通用兜底消息
        // 错误已在上层 catch 中处理，这里只做静默日志
        console.error('Async function error (已在上层处理):', error);
      }
    };
  }
}

export default ErrorHandler;

