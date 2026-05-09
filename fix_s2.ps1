$f = "d:\AgricultureAidSystem\AgricultureAidSystem-main\2.VueAdmin\src\components\index\IndexAsideStatic.vue"
$c = Get-Content -Path $f -Raw -Encoding UTF8

# 侧边栏收起态：主背景深黑 -> 深绿
$c = $c -replace '"background":"#000"', '"background":"#1b5e20"'

# 收起态hover背景 #263445 -> #2e7d32
$c = $c -replace '"background":"#263445"', '"background":"#2e7d32"'
$c = $c -replace "'background':'#263445'", "'background':'#2e7d32'"
$c = $c -replace '"background":"#263445', '"background":"#2e7d32'
$c = $c -replace '#263445!', '#2e7d32!'
$c = $c -replace "'#263445!", "'#2e7d32!"

# 收起态active背景 #1f2d3e -> #388e3c
$c = $c -replace '"background":"#1f2d3e', '"background":"#388e3c'
$c = $c -replace '#1f2d3e!', '#388e3c!'
$c = $c -replace "'#1f2d3e!", "'#388e3c!"

# 收起态其他 #304156 -> #1b5e20
$c = $c -replace '"background":"#304156', '"background":"#1b5e20'
$c = $c -replace '#304156!', '#1b5e20!'

# 收起态hover的红色（错误颜色）
$c = $c -replace 'background:"#1f407a"', 'background:"#388e3c"'

# 收起态字体颜色 #c1d2e2 -> #c8e6c9（淡绿）
$c = $c -replace '"color":"#c1d2e2"', '"color":"#c8e6c9"'
$c = $c -replace "'color':'#c1d2e2'", "'color':'#c8e6c9'"

# 边框颜色 #fbbe62 -> #4caf50（绿色边框）
$c = $c -replace '"border":"0px solid #fbbe62"', '"border":"0px solid #388e3c"'
$c = $c -replace "'border':'0px solid #fbbe62'", "'border':'0px solid #388e3c'"

# 展开态hover背景 #142f40 -> #388e3c
$c = $c -replace '"background":"#142f40', '"background":"#388e3c'
$c = $c -replace '#142f40!', '#388e3c!'
$c = $c -replace "'#142f40!", "'#388e3c!"

# 展开态主背景 #66779d -> #2e7d32（已在IndexMain改，但确保一致性）
$c = $c -replace '"background":"#66779d"', '"background":"#2e7d32"'

Set-Content -Path $f -Value $c -Encoding UTF8 -NoNewline
Write-Host "Sidebar done"
