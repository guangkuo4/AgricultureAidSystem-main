const fs = require('fs');
const path = require('path');

// Validate verticalStyle2.js
const vs2 = fs.readFileSync(path.join(__dirname, '2.VueAdmin/src/assets/styles/verticalStyle2.js'), 'utf8');
const j2 = vs2.replace(/export default/, 'const x =');
const evalCode = j2 + '; console.log("verticalStyle2.js: export OK");';
try {
    eval(evalCode);
} catch(e) {
    console.log('verticalStyle2.js ERROR:', e.message);
}

// Validate home.vue data section
const home = fs.readFileSync(path.join(__dirname, '2.VueAdmin/src/views/home.vue'), 'utf8');
const dataMatch = home.match(/data\(\)\s*\{\s*return\s*\{/);
if (dataMatch) {
    console.log('home.vue data() found at:', dataMatch.index);
    // Find the matching closing brace for the return { ... } in data()
    let depth = 0;
    let started = false;
    let end = -1;
    for (let i = dataMatch.index + dataMatch[0].length; i < home.length; i++) {
        if (home[i] === '{') { depth++; started = true; }
        else if (home[i] === '}') {
            if (!started) { end = i; break; }
            depth--;
            if (depth === 0) { end = i; break; }
        }
    }
    if (end > 0) {
        const dataStr = home.substring(dataMatch.index, end + 1);
        console.log('home.vue data() return block length:', dataStr.length);
        // Check brace balance in chart objects
        const chartObjs = ['line:', 'bar:', 'pie:', 'funnel:', 'gauge:'];
        for (const obj of chartObjs) {
            const idx = dataStr.indexOf(obj);
            if (idx >= 0) {
                let d = 0;
                let s = false;
                for (let i = idx + obj.length; i < dataStr.length; i++) {
                    if (dataStr[i] === '{') { d++; s = true; }
                    else if (dataStr[i] === '}') { d--; if (d === 0) { console.log('  ' + obj + ' balanced at offset', i - idx); break; } }
                }
                if (d !== 0) console.log('  ' + obj + ' UNBALANCED, depth =', d);
            }
        }
        // Check top-level return { ... } balance
        let rd = 0;
        for (const ch of dataStr) {
            if (ch === '{') rd++;
            else if (ch === '}') rd--;
        }
        console.log('home.vue data() return {} balance:', rd === 0 ? 'OK' : 'UNBALANCED (diff=' + rd + ')');
    }
}
