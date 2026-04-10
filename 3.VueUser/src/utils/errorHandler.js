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
    
    let errorType = ERROR_TYPES.UNKNOWN;
    let errorMessage = customMessage || ERROR_MESSAGES[ERROR_TYPES.UNKNOWN];
    
    // 根据错误类型判断
    if (error.message && error.message.includes('Network Error')) {
      errorType = ERROR_TYPES.NETWORK;
      errorMessage = customMessage || ERROR_MESSAGES[ERROR_TYPES.NETWORK];
    } else if (error.response && error.response.status === 401) {
      errorType = ERROR_TYPES.AUTH;
      errorMessage = customMessage || ERROR_MESSAGES[ERROR_TYPES.AUTH];
      // 跳转到登录页面
      setTimeout(() => {
        window.location.href = '/login';
      }, 1000);
    } else if (error.response && error.response.status === 400) {
      errorType = ERROR_TYPES.VALIDATION;
      errorMessage = customMessage || (error.response.data && error.response.data.message) || ERROR_MESSAGES[ERROR_TYPES.VALIDATION];
    } else if (error.response && error.response.status >= 500) {
      errorType = ERROR_TYPES.SERVER;
      errorMessage = customMessage || ERROR_MESSAGES[ERROR_TYPES.SERVER];
    }
    
    // 显示错误提示
    this.showError(errorMessage);
    
    return {
      type: errorType,
      message: errorMessage
    };
  }
  
  /**
   * 显示错误提示
   * @param {string} message - 错误消息
   */
  static showError(message) {
    // 检查是否存在 Element UI 的 $message 方法
    if (window.$message) {
      window.$message.error({
        message: message,
        duration: 3000,
        showClose: true
      });
    } else {
      // 否则使用浏览器默认的 alert
      alert(message);
    }
  }
  
  /**
   * 显示成功提示
   * @param {string} message - 成功消息
   */
  static showSuccess(message) {
    // 检查是否存在 Element UI 的 $message 方法
    if (window.$message) {
      window.$message.success({
        message: message,
        duration: 2000,
        showClose: true
      });
    } else {
      // 否则使用浏览器默认的 alert
      alert(message);
    }
  }
  
  /**
   * 显示警告提示
   * @param {string} message - 警告消息
   */
  static showWarning(message) {
    // 检查是否存在 Element UI 的 $message 方法
    if (window.$message) {
      window.$message.warning({
        message: message,
        duration: 3000,
        showClose: true
      });
    } else {
      // 否则使用浏览器默认的 alert
      alert(message);
    }
  }
  
  /**
   * 显示信息提示
   * @param {string} message - 信息消息
   */
  static showInfo(message) {
    // 检查是否存在 Element UI 的 $message 方法
    if (window.$message) {
      window.$message.info({
        message: message,
        duration: 2000,
        showClose: true
      });
    } else {
      // 否则使用浏览器默认的 alert
      alert(message);
    }
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
        this.handleError(error, customMessage);
        throw error;
      }
    };
  }
}

export default ErrorHandler;