import json

# 读取文件
with open('src/views/home.vue', 'r', encoding='utf-8') as f:
    lines = f.readlines()

# 获取第78行（索引77）
line = lines[77]
print(f"Line length: {len(line)}")

# 提取line对象的值部分（去掉 "line: " 前缀和末尾的逗号）
json_str = line.strip().split('line: ')[1].rstrip(',')

# 尝试解析JSON并捕获错误
try:
    data = json.loads(json_str)
    print("JSON is valid!")
except json.JSONDecodeError as e:
    print(f"JSON decode error: {e}")
    print(f"Error position: {e.pos}")
    print(f"Character at error position: '{json_str[e.pos]}'")
    # 显示错误位置周围的上下文
    start = max(0, e.pos - 20)
    end = min(len(json_str), e.pos + 20)
    print(f"Context around error ({start}-{end}):")
    print(json_str[start:end])
    print(" " * (e.pos - start) + "^")
