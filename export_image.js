const { chromium } = require('playwright');
const path = require('path');
const fs = require('fs');

// Helper function to recursively find all .html files in a directory
function getAllHtmlFiles(dirPath, arrayOfFiles = []) {
    const files = fs.readdirSync(dirPath);

    files.forEach(file => {
        const fullPath = path.join(dirPath, file);
        if (fs.statSync(fullPath).isDirectory()) {
            // Ignore images directory or node_modules
            if (file !== 'images' && file !== 'node_modules' && file !== 'components') {
                getAllHtmlFiles(fullPath, arrayOfFiles);
            }
        } else if (file.endsWith('.html')) {
            arrayOfFiles.push(fullPath);
        }
    });

    return arrayOfFiles;
}

(async () => {
    console.log('🚀 Starting Playwright HTML-to-Image Exporter...');

    // Base HTML directory and Output directory setup
    const iaHtmlDir = path.resolve(__dirname, 'IA/html');
    const outputDir = path.resolve(__dirname, 'ui_ux');

    if (!fs.existsSync(iaHtmlDir)) {
        console.error(`❌ HTML directory not found at: ${iaHtmlDir}`);
        process.exit(1);
    }

    if (!fs.existsSync(outputDir)) {
        fs.mkdirSync(outputDir, { recursive: true });
        console.log(`📁 Created output folder: ${outputDir}`);
    }

    // Discover all .html files dynamically
    const htmlFiles = getAllHtmlFiles(iaHtmlDir);
    console.log(`🔍 Found ${htmlFiles.length} HTML file(s) to export:\n`);
    htmlFiles.forEach((file, index) => {
        console.log(`   ${index + 1}. ${path.relative(__dirname, file)}`);
    });
    console.log('');

    // Launch Chromium Browser in Headless mode
    const browser = await chromium.launch({ headless: true });
    const context = await browser.newContext({
        viewport: { width: 1920, height: 1080 },
        deviceScaleFactor: 2 // High Resolution / Retina quality
    });
    const page = await context.newPage();

    const isForce = process.argv.includes('--force') || process.argv.includes('-f');
    if (isForce) {
        console.log('⚡ --force flag detected: Re-rendering ALL screens regardless of modification time.\n');
    }

    let successCount = 0;
    let skippedCount = 0;

    for (const filePath of htmlFiles) {
        const fileBasename = path.basename(filePath, '.html');
        
        // Determine subfolder based on source location (recruitment, people_management)
        let subFolder = '';
        if (filePath.includes('recruitment')) {
            subFolder = 'recruitment';
        } else if (filePath.includes('people_management')) {
            subFolder = 'people_management';
        } else if (filePath.includes('onboarding')) {
            subFolder = 'onboarding';
        } else if (filePath.includes('offboarding')) {
            subFolder = 'offboarding';
        }

        const targetDir = subFolder ? path.join(outputDir, subFolder) : outputDir;
        if (!fs.existsSync(targetDir)) {
            fs.mkdirSync(targetDir, { recursive: true });
        }

        const outputImagePath = path.join(targetDir, `${fileBasename}.png`);
        const fileUrl = `file:///${filePath.replace(/\\/g, '/')}`;

        // Smart Incremental Check: Skip if image exists and is newer than source HTML
        if (!isForce && fs.existsSync(outputImagePath)) {
            const htmlMtime = fs.statSync(filePath).mtimeMs;
            const imgMtime = fs.statSync(outputImagePath).mtimeMs;
            if (imgMtime >= htmlMtime) {
                console.log(`⏭️  Skipped: [${fileBasename}] (Image is up-to-date)`);
                skippedCount++;
                continue;
            }
        }

        try {
            console.log(`📸 Exporting: [${fileBasename}] ...`);
            await page.goto(fileUrl, { waitUntil: 'networkidle', timeout: 30000 });

            // Small delay to ensure all CSS fonts and dynamic JS render properly
            await page.waitForTimeout(600);

            // Check if page contains standalone component/modal/drawer element
            const targetElement = await page.$('.drawer-preview, .modal-card, .form-card, .timeline-card, .approval-modal');

            if (targetElement && filePath.includes('detail')) {
                // Crop directly to the component card to eliminate black/empty background
                await targetElement.screenshot({
                    path: outputImagePath,
                    type: 'png'
                });
            } else {
                // Capture Full Page Screenshot for main app screens
                await page.screenshot({
                    path: outputImagePath,
                    fullPage: true,
                    type: 'png'
                });
            }

            console.log(`   ✅ Saved -> ${path.relative(__dirname, outputImagePath)}`);
            successCount++;
        } catch (err) {
            console.error(`   ❌ Failed to export [${fileBasename}]:`, err.message);
        }
    }

    await browser.close();

    console.log(`\n🎉 Done! Processed ${htmlFiles.length} file(s): ${successCount} exported, ${skippedCount} skipped (up-to-date).`);
    console.log(`📂 Output folder: ${outputDir}`);
})();
