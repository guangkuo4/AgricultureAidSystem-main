export default {
  isCollapse: false,
  close: {
    contentBox: {
      hover: {},
      active: { margin: '0 0 0 54px' },
      default: {
        minHeight: '100%',
        padding: '0 0 0 64px',
        margin: '0',
        position: 'relative',
        background: '#e8edf2',
        display: 'block'
      }
    },
    box: {
      hover: {},
      active: { width: '54px' },
      default: {
        boxShadow: '2px 0 20px rgba(0,0,0,.3)',
        padding: '0px 0 0',
        borderColor: 'rgba(255,255,255,.06)',
        bottom: '0',
        transition: 'width 0.35s cubic-bezier(0.4, 0, 0.2, 1)',
        overflow: 'hidden',
        top: '0',
        left: '0',
        background: 'linear-gradient(180deg, #0d1b2a 0%, #1a2f4a 40%, #0f2240 100%)',
        borderWidth: '0 1px 0 0',
        width: '64px',
        fontSize: '0px',
        position: 'fixed',
        borderStyle: 'solid',
        height: '100%',
        zIndex: '1001'
      }
    },
    menu: {
      two: {
        title: {
          hover: {
            border: '0px solid rgba(255,215,0,.3)',
            padding: '0 0px',
            color: '#ffd700',
            textAlign: 'center',
            background: 'rgba(255,215,0,.08) !important',
            lineHeight: '40px',
            height: '40px'
          },
          active: {
            border: '0px solid rgba(255,215,0,.3)',
            padding: '0 0px',
            color: '#ffd700',
            textAlign: 'center',
            background: 'rgba(255,215,0,.12) !important',
            lineHeight: '40px',
            height: '40px'
          },
          default: {
            border: '0px solid rgba(255,215,0,.15)',
            padding: '0 0px',
            backgroundColor: 'rgba(255,255,255,.04)',
            color: 'rgba(255,255,255,.65)',
            textAlign: 'center',
            lineHeight: '40px',
            fontSize: '13px',
            height: '40px'
          }
        },
        box: {
          hover: {},
          default: {
            border: 'none',
            padding: '0px 0',
            margin: '0 0 0 3px',
            fontSize: 'inherit',
            borderRadius: '0px'
          }
        }
      },
      box: {
        hover: {},
        default: {
          border: 0,
          padding: '0 4px',
          listStyle: 'none',
          margin: '0',
          flexWrap: 'wrap',
          background: 'none',
          display: 'flex',
          position: 'relative'
        }
      },
      one: {
        box1: {
          hover: {
            color: '#ffd700',
            background: 'rgba(255,215,0,.08)'
          },
          active: {
            color: '#ffd700',
            background: 'rgba(255,215,0,.12)'
          },
          default: {
            cursor: 'pointer',
            padding: '0 8px',
            whiteSpace: 'nowrap',
            color: 'rgba(255,255,255,.8)',
            background: 'none',
            fontSize: 'inherit',
            position: 'relative'
          }
        },
        icon: {
          hover: {},
          default: {
            verticalAlign: 'middle',
            margin: '0',
            color: '#ffd700',
            textAlign: 'center',
            display: 'inline-block',
            width: '42px',
            fontSize: '24px'
          },
          flag: true
        },
        box: {
          hover: {},
          default: {
            padding: '0',
            listStyle: 'none',
            margin: '0',
            fontSize: 'inherit'
          }
        },
        title: {
          hover: {},
          default: {
            width: '0',
            verticalAlign: 'middle',
            fontSize: 'inherit',
            color: 'inherit',
            height: '0'
          }
        },
        arrow: {
          hover: {},
          default: {
            verticalAlign: 'middle',
            margin: '-7px 0 0 0',
            top: '50%',
            color: 'inherit',
            display: 'none',
            fontSize: '12px',
            position: 'absolute',
            right: '20px'
          }
        }
      }
    },
    btn: {
      icon: {
        hover: {},
        default: {
          color: '#ffd700',
          margin: '0 6px 0',
          fontSize: '22px'
        },
        text: 'icon-gengduo'
      },
      hover: { opacity: '0.8' },
      default: {
        border: '0',
        cursor: 'pointer',
        padding: '0 4px',
        margin: '16px 0px 0px',
        color: 'rgba(255,255,255,.6)',
        outline: 'none',
        borderRadius: '8px',
        top: '0px',
        left: '82px',
        background: 'rgba(255,215,0,0)',
        width: 'auto',
        fontSize: '14px',
        position: 'fixed',
        height: 'auto'
      },
      text: ''
    },
    user: {
      two: {
        title: {
          hover: {
            padding: '0 0px',
            backgroundColor: 'rgba(255,215,0,.08) !important',
            lineHeight: '40px',
            color: '#ffd700',
            height: '40px'
          },
          active: {
            padding: '0 0px',
            backgroundColor: 'rgba(255,215,0,.12) !important',
            lineHeight: '40px',
            color: '#ffd700',
            height: '40px'
          },
          default: {
            padding: '0 0px',
            backgroundColor: 'rgba(255,255,255,.04)',
            lineHeight: '40px',
            fontSize: '13px',
            color: 'rgba(255,255,255,.65)',
            height: '40px'
          }
        },
        box: {
          hover: {},
          default: { border: 'none' }
        }
      },
      one: {
        box1: {
          hover: {
            color: '#ffd700',
            background: 'rgba(255,215,0,.08)'
          },
          active: {
            color: '#ffd700',
            background: 'rgba(255,215,0,.12)'
          },
          default: {
            cursor: 'pointer',
            padding: '0 8px',
            whiteSpace: 'nowrap',
            position: 'relative',
            color: 'rgba(255,255,255,.8)',
            background: 'none'
          }
        },
        icon: {
          hover: {},
          default: {
            verticalAlign: 'middle',
            margin: '0',
            color: '#ffd700',
            textAlign: 'center',
            display: 'inline-block',
            width: '42px',
            fontSize: '24px'
          },
          flag: true,
          text: 'icon-kuaijiezhifu'
        },
        box: {
          hover: {},
          default: {
            padding: '0',
            listStyle: 'none',
            margin: '0',
            display: 'block',
            order: '2'
          }
        },
        title: {
          hover: {},
          default: {
            width: '0',
            verticalAlign: 'middle',
            fontSize: 'inherit',
            color: 'inherit',
            height: '0'
          }
        },
        arrow: {
          hover: {},
          default: {
            verticalAlign: 'middle',
            margin: '-7px 0 0 0',
            top: '50%',
            color: 'inherit',
            display: 'none',
            fontSize: '12px',
            position: 'absolute',
            right: '20px'
          }
        }
      }
    },
    userinfo: {
      nickname: {
        hover: {},
        default: {
          fontSize: '22px',
          lineHeight: '1.5',
          color: '#ffd700',
          textAlign: 'center'
        }
      },
      img: {
        hover: {},
        default: {
          width: '100%',
          objectFit: 'cover',
          borderRadius: '20px',
          display: 'block',
          height: '170px'
        }
      },
      box: {
        hover: {},
        default: {
          width: '100%',
          padding: '20px',
          display: 'none',
          height: 'auto'
        }
      }
    },
    home: {
      two: {
        title: {
          hover: {
            padding: '0 0px',
            backgroundColor: 'rgba(255,215,0,.08) !important',
            lineHeight: '40px',
            color: '#ffd700',
            height: '40px'
          },
          active: {
            padding: '0 0px',
            backgroundColor: 'rgba(255,215,0,.12) !important',
            lineHeight: '40px',
            color: '#ffd700',
            height: '40px'
          },
          default: {
            padding: '0 0px',
            backgroundColor: 'rgba(255,255,255,.04)',
            lineHeight: '40px',
            color: 'rgba(255,255,255,.65)',
            height: '40px'
          }
        },
        box: {
          hover: {},
          default: { border: 'none' }
        }
      },
      one: {
        box1: {
          hover: {
            color: '#ffd700',
            background: 'rgba(255,215,0,.08)'
          },
          active: {
            color: '#ffd700',
            background: 'rgba(255,215,0,.12)'
          },
          default: {
            cursor: 'pointer',
            padding: '0 8px',
            whiteSpace: 'nowrap',
            color: 'rgba(255,255,255,.8)',
            background: 'none',
            fontSize: 'inherit',
            position: 'relative'
          }
        },
        icon: {
          hover: {},
          default: {
            verticalAlign: 'middle',
            margin: '0',
            color: '#ffd700',
            textAlign: 'center',
            display: 'inline-block',
            width: '42px',
            fontSize: '24px'
          },
          flag: true,
          text: 'icon-home8'
        },
        box: {
          hover: {},
          default: {
            padding: '0',
            listStyle: 'none',
            margin: '0',
            display: 'block'
          }
        },
        title: {
          hover: {},
          default: {
            width: '0',
            verticalAlign: 'middle',
            fontSize: 'inherit',
            color: 'inherit',
            height: '0'
          }
        },
        arrow: {
          hover: {},
          default: {
            verticalAlign: 'middle',
            margin: '-7px 0 0 0',
            top: '50%',
            color: 'inherit',
            display: 'none',
            fontSize: '12px',
            position: 'absolute',
            right: '20px'
          }
        }
      }
    }
  },
  open: {
    contentBox: {
      hover: {},
      default: {
        minHeight: '100%',
        padding: '0 0 0 250px',
        margin: '0',
        position: 'relative',
        background: '#e8edf2',
        display: 'block'
      }
    },
    box: {
      hover: {},
      default: {
        boxShadow: '2px 0 20px rgba(0,0,0,.3)',
        padding: '70px 0 10px',
        borderColor: 'rgba(255,255,255,.06)',
        bottom: '0',
        transition: 'width 0.35s cubic-bezier(0.4, 0, 0.2, 1)',
        overflow: 'hidden',
        top: '0',
        left: '0',
        background: 'linear-gradient(180deg, #0d1b2a 0%, #1a2f4a 40%, #0f2240 100%)',
        borderWidth: '0 1px 0 0',
        width: '250px',
        fontSize: '14px',
        position: 'fixed',
        borderStyle: 'solid',
        height: '100%',
        zIndex: '1001'
      }
    },
    menu: {
      two: {
        title: {
          hover: {
            width: '88%',
            padding: '0 14px',
            lineHeight: '42px',
            color: '#ffd700',
            background: 'rgba(255,215,0,.08)',
            height: '42px'
          },
          active: {
            width: '88%',
            padding: '0 14px',
            lineHeight: '42px',
            color: '#ffd700',
            background: 'rgba(255,215,0,.12)',
            height: '42px'
          },
          default: {
            padding: '0 14px',
            margin: '0px auto',
            borderColor: 'rgba(255,215,0,.15)',
            color: 'rgba(255,255,255,.65)',
            textAlign: 'left',
            borderRadius: '0px',
            background: 'rgba(255,255,255,.04)',
            borderWidth: '0 0 0px',
            width: '88%',
            lineHeight: '42px',
            fontSize: '13px',
            borderStyle: 'dashed',
            height: '42px'
          }
        },
        box: {
          hover: {},
          default: {
            border: '0px solid rgba(255,215,0,.15)',
            width: '100%',
            margin: '6px auto 0',
            fontSize: 'inherit',
            borderRadius: '0px',
            background: 'none'
          }
        }
      },
      box: {
        hover: {},
        default: {
          border: 0,
          padding: '0 0px',
          listStyle: 'none',
          margin: '0',
          alignItems: 'flex-start',
          flexWrap: 'wrap',
          background: 'none',
          display: 'flex',
          position: 'relative'
        }
      },
      one: {
        box1: {
          hover: {
            color: '#ffd700',
            background: 'rgba(255,215,0,.08)'
          },
          active: {
            color: '#ffd700',
            background: 'rgba(255,215,0,.12)'
          },
          default: {
            cursor: 'pointer',
            border: '0px solid rgba(255,215,0,.15)',
            padding: '14px 20px 14px',
            whiteSpace: 'nowrap',
            color: 'rgba(255,255,255,.88)',
            borderRadius: '0px',
            background: 'rgba(255,255,255,.03)',
            fontSize: '13px',
            lineHeight: '1.2',
            position: 'relative',
            height: 'auto'
          }
        },
        icon: {
          hover: {},
          default: {
            verticalAlign: 'middle',
            margin: '0 8px 0 0',
            color: '#ffd700',
            textAlign: 'left',
            display: 'inline-block',
            width: 'auto',
            fontSize: '18px'
          },
          flag: true
        },
        box: {
          hover: {},
          default: {
            width: '100%',
            padding: '0px',
            listStyle: 'none',
            margin: '0 0 0px',
            lineHeight: 'auto',
            height: 'auto'
          }
        },
        title: {
          hover: {},
          default: {
            verticalAlign: 'middle',
            color: 'inherit',
            textAlign: 'left',
            display: 'inline-block',
            width: 'auto',
            fontSize: 'inherit',
            lineHeight: 'auto'
          }
        },
        arrow: {
          hover: {},
          default: {
            verticalAlign: 'middle',
            margin: '-7px 0 0 0',
            top: '50%',
            color: 'inherit',
            fontSize: 'inherit',
            position: 'absolute',
            right: '20px'
          }
        }
      }
    },
    btn: {
      icon: {
        hover: {},
        default: {
          color: '#ffd700',
          margin: '0px 2px',
          fontSize: '22px'
        },
        text: 'icon-gengduo'
      },
      hover: { opacity: '0.8' },
      default: {
        border: '0px solid rgba(255,215,0,.15)',
        cursor: 'pointer',
        padding: '0 4px',
        margin: '16px 4px 0px',
        color: 'rgba(255,255,255,.6)',
        display: 'none',
        outline: 'none',
        borderRadius: '8px',
        top: '0px',
        left: '230px',
        background: 'rgba(255,215,0,0)',
        width: 'auto',
        fontSize: 'inherit',
        position: 'fixed',
        height: 'auto'
      },
      text: ''
    },
    user: {
      two: {
        title: {
          hover: {
            width: '88%',
            padding: '0 14px',
            lineHeight: '42px',
            color: '#ffd700',
            background: 'rgba(255,215,0,.08)',
            height: '42px'
          },
          active: {
            width: '88%',
            padding: '0 14px',
            lineHeight: '42px',
            color: '#ffd700',
            background: 'rgba(255,215,0,.12)',
            height: '42px'
          },
          default: {
            padding: '0 14px',
            borderColor: 'rgba(255,215,0,.15)',
            margin: '0px auto',
            color: 'rgba(255,255,255,.65)',
            textAlign: 'left',
            background: 'rgba(255,255,255,.04)',
            borderWidth: '0 0 0px',
            width: '88%',
            lineHeight: '42px',
            fontSize: '13px',
            borderStyle: 'dashed',
            height: '42px'
          }
        },
        box: {
          hover: {},
          default: {
            border: '0px solid rgba(255,215,0,.15)',
            margin: '6px 0 0',
            fontSize: 'inherit',
            borderRadius: '0px',
            background: 'none'
          }
        }
      },
      one: {
        box1: {
          hover: {
            color: '#ffd700',
            background: 'rgba(255,215,0,.08)',
            height: 'auto'
          },
          active: {
            color: '#ffd700',
            background: 'rgba(255,215,0,.12)',
            height: 'auto'
          },
          default: {
            cursor: 'pointer',
            padding: '14px 20px 14px',
            whiteSpace: 'nowrap',
            color: 'rgba(255,255,255,.88)',
            borderRadius: '0px',
            background: 'rgba(255,255,255,.03)',
            fontSize: '13px',
            lineHeight: '1.2',
            position: 'relative',
            height: 'auto'
          }
        },
        icon: {
          hover: {},
          default: {
            verticalAlign: 'middle',
            margin: '0 8px 0 0',
            color: '#ffd700',
            textAlign: 'left',
            display: 'inline-block',
            width: 'auto',
            fontSize: '18px'
          },
          flag: true,
          text: 'icon-kuaijiezhifu'
        },
        box: {
          hover: {},
          default: {
            padding: '0px',
            listStyle: 'none',
            margin: '0',
            display: 'block',
            width: '100%',
            lineHeight: 'auto',
            order: '2',
            height: 'auto'
          }
        },
        title: {
          hover: {},
          default: {
            verticalAlign: 'middle',
            color: 'inherit',
            textAlign: 'left',
            display: 'inline-block',
            width: 'auto',
            fontSize: 'inherit',
            lineHeight: 'auto'
          },
          text: '个人中心'
        },
        arrow: {
          hover: {},
          default: {
            verticalAlign: 'middle',
            margin: '-7px 0 0 0',
            top: '50%',
            color: 'inherit',
            fontSize: 'inherit',
            position: 'absolute',
            right: '20px'
          }
        }
      }
    },
    userinfo: {
      nickname: {
        hover: {},
        default: {
          fontSize: '20px',
          lineHeight: '1.5',
          color: '#ffd700',
          textAlign: 'center',
          display: 'none'
        }
      },
      img: {
        hover: {},
        default: {
          width: '72px',
          margin: '0 auto',
          objectFit: 'cover',
          borderRadius: '100%',
          display: 'block',
          height: '72px'
        }
      },
      box: {
        hover: {},
        default: {
          padding: '0 0 20px',
          margin: '0px auto',
          background: 'none',
          display: 'none',
          width: '100%',
          height: 'auto',
          order: '2'
        }
      }
    },
    home: {
      two: {
        title: {
          hover: {
            padding: '0 20px',
            lineHeight: '50px',
            color: '#ffd700',
            background: 'rgba(255,215,0,.08)',
            height: '50px'
          },
          active: {
            padding: '0 20px',
            lineHeight: '50px',
            color: '#ffd700',
            background: 'rgba(255,215,0,.12)',
            height: '50px'
          },
          default: {
            padding: '0 20px',
            lineHeight: '50px',
            color: 'rgba(255,255,255,.65)',
            background: 'rgba(255,255,255,.03)',
            height: '50px'
          }
        },
        box: {
          hover: {},
          default: { border: 'none', display: 'none' }
        }
      },
      one: {
        box1: {
          hover: {
            color: '#ffd700',
            background: 'rgba(255,215,0,.08)'
          },
          active: {
            color: '#ffd700',
            background: 'rgba(255,215,0,.12)'
          },
          default: {
            cursor: 'pointer',
            border: '0px solid rgba(255,215,0,.15)',
            padding: '14px 20px 14px',
            whiteSpace: 'nowrap',
            color: 'rgba(255,255,255,.88)',
            borderRadius: '0px',
            background: 'rgba(255,255,255,.03)',
            fontSize: '13px',
            lineHeight: '1.2',
            position: 'relative',
            height: 'auto'
          }
        },
        icon: {
          hover: {},
          default: {
            verticalAlign: 'middle',
            margin: '0 8px 0 0',
            color: '#ffd700',
            textAlign: 'left',
            display: 'inline-block',
            width: 'auto',
            fontSize: '18px'
          },
          flag: true,
          text: 'icon-home8'
        },
        box: {
          hover: {},
          default: {
            padding: '0px',
            listStyle: 'none',
            margin: '0 0 0px',
            display: 'none',
            width: '100%',
            fontSize: 'inherit',
            lineHeight: 'auto',
            height: 'auto'
          }
        },
        title: {
          hover: {},
          default: {
            width: 'auto',
            verticalAlign: 'middle',
            fontSize: 'inherit',
            color: 'inherit',
            textAlign: 'left',
            display: 'inline-block'
          },
          text: '首页'
        },
        arrow: {
          hover: {},
          default: {
            verticalAlign: 'middle',
            margin: '-7px 0 0 0',
            top: '50%',
            color: 'inherit',
            fontSize: '12px',
            position: 'absolute',
            right: '20px'
          }
        }
      }
    }
  }
}
