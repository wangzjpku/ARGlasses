//
//  BGViewController.h
//  Xcode-Example
//
//  Created by  on 9/8/12.
//  Copyright (c) 2012 Active Theory Inc. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface BGViewController : UIViewController
@property (weak, nonatomic) IBOutlet UIView *feedbackView;
@property (weak, nonatomic) IBOutlet UISegmentedControl *playerControl;
- (IBAction)playerCountChanged:(id)sender;

@end
