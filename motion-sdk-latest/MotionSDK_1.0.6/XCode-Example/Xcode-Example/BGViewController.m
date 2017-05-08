//
//  BGViewController.m
//  Xcode-Example
//
//  Created by  on 9/8/12.
//  Copyright (c) 2012 Active Theory Inc. All rights reserved.
//

#import "BGViewController.h"
#import "BGBodyReadingObservable.h"

@implementation BGViewController
@synthesize feedbackView;
@synthesize playerControl;

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    [[BGBodyReadingObservable sharedBodyReadingObservable] addObserver:self forKeyPath:BODY_READING_KEYPATH];
    [[BGBodyReadingObservable sharedBodyReadingObservable] beginRenderingFeedbackToCALayer:[feedbackView layer]];

}

- (void)viewDidUnload
{
    [self setFeedbackView:nil];
    [self setPlayerControl:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
}

- (void)viewWillDisappear:(BOOL)animated
{
	[super viewWillDisappear:animated];
}

- (void)viewDidDisappear:(BOOL)animated
{
	[super viewDidDisappear:animated];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    if ([[UIDevice currentDevice] userInterfaceIdiom] == UIUserInterfaceIdiomPhone) {
        return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
    } else {
        return YES;
    }
}


-(void)observeValueForKeyPath:(NSString *)keyPath ofObject:(id)object change:(NSDictionary *)change context:(void *)context
{
    if ([keyPath isEqual:BODY_READING_KEYPATH])
    {
        NSMutableArray * positions = [change objectForKey:NSKeyValueChangeNewKey];
        for(BGBodyReading* position in positions){
            NSLog(@"x: %f player: %d", [position x], [position playerNumber]);
        }
    }
}


- (IBAction)playerCountChanged:(id)sender {
    if([playerControl selectedSegmentIndex] == 0){
        [[BGBodyReadingObservable sharedBodyReadingObservable] setPlayerCount:1];
    } else {
        [[BGBodyReadingObservable sharedBodyReadingObservable] setPlayerCount:2];

    }
}
@end
