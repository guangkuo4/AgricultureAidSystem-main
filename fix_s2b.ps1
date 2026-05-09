$f = "d:\AgricultureAidSystem\AgricultureAidSystem-main\2.VueAdmin\src\components\index\IndexAsideStatic.vue"
$c = Get-Content -Path $f -Raw -Encoding UTF8

# CSS部分：#263445 -> #2e7d32 (收起态hover背景)
$c = $c -replace 'background: #263445;', 'background: #2e7d32;'
$c = $c -replace 'background: #263445 !important;', 'background: #2e7d32 !important;'

# CSS部分：#304156 -> #1b5e20 (收起态背景)
$c = $c -replace 'background-color: #304156;', 'background-color: #1b5e20;'

# CSS部分：el-submenu hover的边框颜色 #fbbe62 -> #4caf50
$c = $c -replace 'border: 0px solid #fbbe62;', 'border: 0px solid #388e3c;'
$c = $c -replace 'border: 0px solid #fbbe62 !important;', 'border: 0px solid #388e3c !important;'

# CSS部分：字体颜色 #999 -> #a5d6a7 (淡绿色，让文字更协调)
$c = $c -replace 'color: #999 !important;', 'color: #a5d6a7 !important;'
$c = $c -replace 'color: #999;', 'color: #a5d6a7;'

Set-Content -Path $f -Value $c -Encoding UTF8 -NoNewline
Write-Host "CSS part done"
