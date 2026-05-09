with open('src/views/home.vue', 'r', encoding='utf-8') as f:
    lines = f.readlines()

# 获取第78行（索引77）
line = lines[77]
print(f"Line length: {len(line)}")

# 找到第3993个字符附近的内容
error_pos = 3993
start = max(0, error_pos - 30)
end = min(len(line), error_pos + 30)

print(f"\nError position: {error_pos}")
print(f"Context around error ({start}-{end}):")
print(line[start:end])
print(" " * (error_pos - start) + "^")

# 检查JSON结构
import json
try:
    # 提取line对象的值部分
    json_str = line.strip().split('line: ')[1].rstrip(',')
    data = json.loads(json_str)
    print("\nJSON is valid!")
except json.JSONDecodeError as e:
    print(f"\nJSON decode error: {e}")
    print(f"Error position in JSON string: {e.pos}")
